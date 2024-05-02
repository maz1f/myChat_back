package com.chat.MyChat.exception;

public class InvalidRefreshToken extends Exception{
    public InvalidRefreshToken(String message) {
        super(message);
    }
}
