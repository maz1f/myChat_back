package com.chat.MyChat.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Entity
@NoArgsConstructor
public class MessageEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Setter
    private String message;

    @Setter
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private UserEntity sender;

    @Setter
    @ManyToOne
    @JoinColumn(name = "recipient_id")
    private UserEntity recipient;

    @Setter
    private Date sentDate;

}
