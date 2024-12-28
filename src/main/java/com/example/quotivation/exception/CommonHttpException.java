package com.example.quotivation.exception;

import org.springframework.http.HttpStatus;

public abstract class CommonHttpException extends RuntimeException {

    private final HttpStatus httpStatus;

    public CommonHttpException(String message, HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
