package com.chat.MyChat.controller;

import com.chat.MyChat.dto.MessageRequest;
import com.chat.MyChat.exception.ChatNotFoundException;
import com.chat.MyChat.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
public class MessageController {

    @Autowired
    private MessageService messageService;

    @PostMapping("/send")
    public ResponseEntity<?> sendMessage(@RequestBody MessageRequest message){
        try {
            messageService.sendMessage(message);
        } catch (ChatNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        return ResponseEntity.ok("Message sent");
    }

}
