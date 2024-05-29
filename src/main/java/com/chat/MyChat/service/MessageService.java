package com.chat.MyChat.service;

import com.chat.MyChat.dto.MessageRequest;
import com.chat.MyChat.entity.MessageEntity;
import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.model.User;
import com.chat.MyChat.repo.MessageRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserService userService;

    public void sendMessage(MessageRequest message) throws ChatNotFoundException {
        Optional<UserEntity> sender = userService.findByUsername(message.getSender());
        Optional<UserEntity> recipient = userService.findByUsername(message.getRecipient());
        if (sender.isEmpty() || recipient.isEmpty())
            throw new ChatNotFoundException();
        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setMessage(message.getMessage());
        messageEntity.setSender(sender.get());
        messageEntity.setRecipient(recipient.get());
        messageEntity.setSentDate(new Date());
        messageRepo.save(messageEntity);
    }
}
