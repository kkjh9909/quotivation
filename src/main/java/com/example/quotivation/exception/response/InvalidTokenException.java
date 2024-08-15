package com.example.quotivation.exception.response;

import org.springframework.security.core.AuthenticationException;

public class InvalidTokenException extends AuthenticationException {

    public InvalidTokenException(String msg) {
        super(msg);
    }

    public InvalidTokenException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
