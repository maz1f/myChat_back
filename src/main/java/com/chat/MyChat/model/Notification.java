package com.chat.MyChat.model;

import com.chat.MyChat.entity.NotificationEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Notification {
    private String notification;
    public static Notification toModel(NotificationEntity notificationEntity) {
        Notification notification = new Notification();
        notification.setNotification(notificationEntity.getNotification());
        return notification;
    }
}
