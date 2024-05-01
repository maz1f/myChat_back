package com.chat.MyChat.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*")
public class MessageController {

    @GetMapping("/test")
    public String test() {
        return "You are logged in";
    }
}
