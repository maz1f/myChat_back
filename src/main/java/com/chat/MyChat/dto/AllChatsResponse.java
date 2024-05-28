package com.chat.MyChat.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AllChatsResponse {
    private List<String> users;
}
