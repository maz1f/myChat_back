package com.chat.MyChat.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String email;
    private Boolean isOnline;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "sender")
    private List<MessageEntity> sentMessage;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "recipient")
    private List<MessageEntity> receivedMessages;

    public UserEntity() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<MessageEntity> getSentMessage() {
        return sentMessage;
    }

    public void setSentMessage(List<MessageEntity> sentMessage) {
        this.sentMessage = sentMessage;
    }

    public List<MessageEntity> getReceivedMessages() {
        return receivedMessages;
    }

    public void setReceivedMessages(List<MessageEntity> receivedMessages) {
        this.receivedMessages = receivedMessages;
    }

    public Boolean getOnline() {
        return isOnline;
    }

    public void setOnline(Boolean online) {
        isOnline = online;
    }
}
