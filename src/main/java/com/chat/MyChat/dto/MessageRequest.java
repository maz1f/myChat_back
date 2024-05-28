package com.chat.MyChat.dto;

import lombok.Data;

@Data
public class MessageRequest {
    private String message;
    private String sender;
    private String recipient;
}
