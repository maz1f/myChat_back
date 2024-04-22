package com.chat.MyChat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Entity
@NoArgsConstructor
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String username;

    @Setter
    private String password;

    @Setter
    private String email;

    @Setter
    private Boolean isOnline;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<MessageEntity> sentMessage;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipient")
    private List<MessageEntity> receivedMessages;

    public void addSetMessage(MessageEntity message) {
        this.sentMessage.add(message);
    }

    public void addReceivedMessage(MessageEntity message) {
        this.receivedMessages.add(message);
    }

}
