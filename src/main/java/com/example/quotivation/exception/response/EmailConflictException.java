package com.example.quotivation.exception.response;

public class EmailConflictException extends RuntimeException {

    public EmailConflictException(String message) {
        super(message);
    }
}