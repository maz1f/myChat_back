package com.chat.MyChat.dto;

import com.chat.MyChat.model.Chat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class AllChatsResponse {
    private List<Chat> chats;
}
