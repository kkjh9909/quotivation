package com.example.quotivation.exception.response;

import com.example.quotivation.exception.CommonHttpException;
import org.springframework.http.HttpStatus;

public class EmailConflictException extends CommonHttpException {

    public EmailConflictException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}