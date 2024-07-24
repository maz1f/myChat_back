package com.chat.MyChat.controller;

import com.chat.MyChat.dto.AllChatsResponse;
import com.chat.MyChat.dto.MessagesResponse;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.service.ChatService;
import com.chat.MyChat.service.UserService;
import com.chat.MyChat.util.JwtTokenUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
@RestController
public class ChatController {

    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;


    @GetMapping("/getChats")
    public AllChatsResponse getAllChats(@RequestHeader("Authorization") String token) {
        String username = jwtTokenUtils.getUsernameByToken(token.substring(7));
        return AllChatsResponse.builder()
                .chats(chatService.chatsWithNewMessages(username))
                .build();
    }

    @GetMapping("/chat/{sender}/{recipient}")
    public MessagesResponse getChat(@PathVariable String sender, @PathVariable String recipient) throws ChatNotFoundException {
        return MessagesResponse.builder()
                .messages(chatService.getMessages(sender, recipient))
                .build();
    }

}
