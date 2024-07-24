package com.chat.MyChat.service;

import com.chat.MyChat.dto.MessageRequest;
import com.chat.MyChat.entity.MessageEntity;
import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.model.Chat;
import com.chat.MyChat.repo.MessageRepo;
import com.chat.MyChat.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MessageService {
    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepo userRepo;

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

    public int countNewMessagesFromUser(String sender, String recipient) throws ChatNotFoundException {
        Optional<UserEntity> senderUser = userService.findByUsername(sender);
        Optional<UserEntity> recipientUser = userService.findByUsername(recipient);
        if (senderUser.isEmpty() || recipientUser.isEmpty())
            throw new ChatNotFoundException();
        return messageRepo.countAllBySenderUsernameAndRecipientUsernameAndIsReadFalse(senderUser.get().getUsername(), recipientUser.get().getUsername());
    }


}
