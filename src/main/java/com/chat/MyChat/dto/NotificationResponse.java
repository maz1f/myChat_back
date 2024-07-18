package com.chat.MyChat.dto;

import com.chat.MyChat.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private List<Notification> notifications;
}
