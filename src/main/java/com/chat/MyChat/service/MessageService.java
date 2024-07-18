package com.chat.MyChat.service;

import com.chat.MyChat.dto.MessageRequest;
import com.chat.MyChat.entity.MessageEntity;
import com.chat.MyChat.entity.NotificationEntity;
import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.repo.MessageRepo;
import com.chat.MyChat.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

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

    public void addNotification(String username, String notification) {
        Optional<UserEntity> user = userService.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(username);
        NotificationEntity notificationEntity = new NotificationEntity();
        notificationEntity.setNotification(notification);
        user.get().addNotification(notificationEntity);
        userRepo.save(user.get());
    }

}
