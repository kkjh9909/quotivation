package com.example.quotivation.exception.response;

import com.example.quotivation.exception.CommonHttpException;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;

public class TokenExpiredException extends CommonHttpException {

    public TokenExpiredException(String message, HttpStatus httpStatus) {
        super(message, httpStatus);
    }
}
