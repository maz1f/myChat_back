package com.chat.MyChat.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class Chat {
    private String username;
    private int numberOfNewMessages;
    public static Chat newChat(String username, int number) {
        Chat newChat = new Chat();
        newChat.setUsername(username);
        newChat.setNumberOfNewMessages(number);
        return newChat;
    }
}
