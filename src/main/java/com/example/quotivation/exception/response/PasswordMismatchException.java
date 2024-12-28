package com.example.quotivation.exception.response;

import com.example.quotivation.exception.CommonHttpException;
import org.springframework.http.HttpStatus;

public class PasswordMismatchException extends CommonHttpException {

    public PasswordMismatchException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
