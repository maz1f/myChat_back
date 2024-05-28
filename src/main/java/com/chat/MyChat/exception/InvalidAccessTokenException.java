package com.chat.MyChat.exception;

public class InvalidAccessTokenException extends Exception{
    public InvalidAccessTokenException() {
        super("Invalid access token");
    }
}
