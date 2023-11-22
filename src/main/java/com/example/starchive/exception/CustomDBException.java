package com.example.starchive.exception;

public class CustomDBException extends RuntimeException{
    public CustomDBException(String message) {
        super(message);
    }
}
