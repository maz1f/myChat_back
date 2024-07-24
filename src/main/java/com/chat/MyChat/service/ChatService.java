package com.chat.MyChat.service;

import com.chat.MyChat.dto.MessageRequest;
import com.chat.MyChat.entity.MessageEntity;
import com.chat.MyChat.entity.UserEntity;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.exception.InvalidAccessTokenException;
import com.chat.MyChat.model.Chat;
import com.chat.MyChat.model.Message;
import com.chat.MyChat.repo.MessageRepo;
import com.chat.MyChat.repo.UserRepo;
import com.chat.MyChat.util.JwtTokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Slf4j
public class ChatService {

    @Autowired
    private MessageRepo messageRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    public boolean isChatExist(String sender, String receiver) {
        return userRepo.findByUsername(sender).isPresent() && userRepo.findByUsername(receiver).isPresent();
    }

    public List<Message> getMessages(String sender, String recipient) throws ChatNotFoundException {
        if (!isChatExist(sender, recipient))
            throw new ChatNotFoundException();
        if (sender.equals(recipient)) {
            return messageRepo.getAllBySenderUsernameAndRecipientUsername(sender, recipient)
                    .stream()
                    .map(msg -> Message.toModel(msg, "received"))
                    .sorted(Comparator.comparing(Message::getSentDate)).toList();
        }
        return Stream.concat(
                messageRepo.getAllBySenderUsernameAndRecipientUsername(sender, recipient).stream().map(msg -> Message.toModel(msg, "sent")),
                messageRepo.getAllBySenderUsernameAndRecipientUsername(recipient, sender).stream().map(msg -> Message.toModel(msg, "received"))
        ).sorted(Comparator.comparing(Message::getSentDate)).toList();

    }

    public List<Chat> chatsWithNewMessages(String username) {
        Optional<UserEntity> user = userService.findByUsername(username);
        if (user.isEmpty())
            throw new UsernameNotFoundException(username);
        Map<String, List<MessageEntity>> chatsWithNewMessages = user.get().getReceivedMessages().stream()
                .filter(msg -> !msg.getIsRead())
                .collect(Collectors.groupingBy(msg -> msg.getSender().getUsername()));
        List<Chat> chats = userService.getAllUsers().stream().map(u -> Chat.newChat(u.getUsername(), 0)).collect(Collectors.toList());
        chats.forEach(chat -> {
                    if (chatsWithNewMessages.containsKey(chat.getUsername()))
                        chat.setNumberOfNewMessages(chatsWithNewMessages.get(chat.getUsername()).size());
                });
        return chats;
    }

}
