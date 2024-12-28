package com.example.quotivation.exception;

import com.example.quotivation.exception.response.EmailConflictException;
import com.example.quotivation.exception.response.PasswordMismatchException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.security.auth.login.LoginException;

@ControllerAdvice
@RequiredArgsConstructor
public class GlobalExceptionController {


    @ExceptionHandler(LoginException.class)
    public ResponseEntity<?> handleLoginException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<?> handleRuntimeException(RuntimeException e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(CommonHttpException.class)
    public ResponseEntity<?> handleCommonException(CommonHttpException e) {
        return ResponseEntity.status(e.getHttpStatus()).body(e.getMessage());
    }
}
