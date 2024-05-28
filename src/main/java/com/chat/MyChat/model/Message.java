package com.chat.MyChat.model;

import com.chat.MyChat.entity.MessageEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@NoArgsConstructor
@Getter
@Setter
public class Message {
    private String message;
    private String sender;
    private String recipient;
    private Date sentDate;

    public static Message toModel(MessageEntity messageEntity) {
        Message message = new Message();
        message.setMessage(messageEntity.getMessage());
        message.setSender(messageEntity.getSender().getUsername());
        message.setRecipient(messageEntity.getRecipient().getUsername());
        message.setSentDate(messageEntity.getSentDate());
        return message;
    }

}
