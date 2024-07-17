package com.chat.MyChat.controller;

import com.chat.MyChat.dto.AllChatsResponse;
import com.chat.MyChat.dto.MessagesResponse;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.service.ChatService;
import com.chat.MyChat.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.context.SecurityContextHolder;
@RestController
//@CrossOrigin(origins = "*")
public class ChatController {

    @Autowired
    private UserService userService;
    @Autowired
    private ChatService chatService;


    @GetMapping("/getChats")
    public AllChatsResponse getAllChats() {
        return AllChatsResponse.builder()
                .users(userService.getAllUsernames())
                .build();
    }

    @GetMapping("/chat/{sender}/{recipient}")
    public MessagesResponse getChat(@PathVariable String sender, @PathVariable String recipient) throws ChatNotFoundException {
        return MessagesResponse.builder()
                .messages(chatService.getMessages(sender, recipient))
                .build();
    }

}
