package com.example.quotivation.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.LoginException;

@ControllerAdvice
public class GlobalExceptionController {

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> handleLoginException(RuntimeException e) {
        return ResponseEntity.status(401).body("로그인 오류");
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(404).body("404 not found");
    }
}
