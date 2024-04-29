package com.chat.MyChat.dto;

import lombok.Data;

@Data
public class JwtRequest {
    private String username;
    private String password;
}
