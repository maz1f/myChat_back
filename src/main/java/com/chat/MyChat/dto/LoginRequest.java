package com.chat.MyChat.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
