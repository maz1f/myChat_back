package com.chat.MyChat.dto;

import com.chat.MyChat.model.Message;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class MessagesResponse {
    private List<Message> messages;
}
