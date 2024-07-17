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
    private Long id;
    private String message;
    private String sender;
    private String recipient;
    private String sentDate;
    private String type;

    public static Message toModel(MessageEntity messageEntity, String type) {
        Message message = new Message();
        message.id = messageEntity.getId();
        message.setMessage(messageEntity.getMessage());
        message.setSender(messageEntity.getSender().getUsername());
        message.setRecipient(messageEntity.getRecipient().getUsername());
        message.setSentDate(messageEntity.getSentDate().toString());
        message.setType(type);
        return message;
    }

}
