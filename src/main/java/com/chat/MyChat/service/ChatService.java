package com.chat.MyChat.service;

import com.chat.MyChat.dto.MessageRequest;
import com.chat.MyChat.entity.MessageEntity;
import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.exception.InvalidAccessTokenException;
import com.chat.MyChat.model.Message;
import com.chat.MyChat.repo.MessageRepo;
import com.chat.MyChat.repo.UserRepo;
import com.chat.MyChat.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

@Service
public class ChatService {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private JwtTokenUtils jwtTokenUtils;

    public boolean isChatExist(String sender, String receiver) {
        return userRepo.findByUsername(sender).isPresent() && userRepo.findByUsername(receiver).isPresent();
    }

    public List<Message> getMessages(String sender, String recipient) throws ChatNotFoundException {
        if (!isChatExist(sender, recipient))
            throw new ChatNotFoundException();
        return Stream.concat(
                messageRepo.getAllBySenderUsernameAndRecipientUsername(sender, recipient).stream().map(msg -> Message.toModel(msg, "sent")),
                messageRepo.getAllBySenderUsernameAndRecipientUsername(recipient, sender).stream().map(msg -> Message.toModel(msg, "received"))
        ).sorted(Comparator.comparing(Message::getSentDate)).toList();

    }



}
