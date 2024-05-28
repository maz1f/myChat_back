package com.chat.MyChat.exception;

public class ChatNotFoundException extends Exception{
    public ChatNotFoundException(){
        super("Chat not found");
    }
}
