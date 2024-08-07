package com.chat.MyChat.controller;

import com.chat.MyChat.dto.MessageRequest;
import com.chat.MyChat.dto.MessagesResponse;
import com.chat.MyChat.dto.NotificationResponse;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.service.ChatService;
import com.chat.MyChat.service.MessageService;
import com.chat.MyChat.service.UserService;
import com.chat.MyChat.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
public class MessageController {

    @Autowired
    private MessageService messageService;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ChatService chatService;

    @MessageMapping("/send")
    public void sendMessage(MessageRequest message) throws ChatNotFoundException {
        log.info("Message received: {}", message);
        messageService.sendMessage(message);
        messagingTemplate.convertAndSendToUser(
                message.getRecipient(),
                "/messages",
                MessagesResponse.builder()
                        .messages(chatService.getMessages(message.getRecipient(), message.getSender()))
                        .build()
        );
        if (!message.getSender().equals(message.getRecipient())) {
            messagingTemplate.convertAndSendToUser(
                    message.getSender(),
                    "/messages",
                    MessagesResponse.builder()
                            .messages(chatService.getMessages(message.getSender(), message.getRecipient()))
                            .build()
            );
            messagingTemplate.convertAndSendToUser(
                    message.getRecipient(),
                    "/messages",
                    NotificationResponse.builder()
                            .notification("You have received a new message from " + message.getSender())
                            .sender(message.getSender())
                            .build()
            );
        }
    }

}
