package com.chat.MyChat.controller;

import com.chat.MyChat.dto.MessageRequest;
import com.chat.MyChat.dto.MessagesResponse;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.model.ChatNotification;
import com.chat.MyChat.model.Message;
import com.chat.MyChat.service.ChatService;
import com.chat.MyChat.service.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
//@CrossOrigin(origins = "*")
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
                message.getSender(),
                "/messages",
                MessagesResponse.builder()
                        .messages(chatService.getMessages(message.getSender(), message.getRecipient()))
                        .build()
        );
        messagingTemplate.convertAndSendToUser(
                message.getRecipient(),
                "/messages",
                MessagesResponse.builder()
                        .messages(chatService.getMessages(message.getRecipient(), message.getSender()))
                        .build()
        );
    }

}
