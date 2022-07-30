package com.devsh0.chirp.advice;

import com.devsh0.chirp.dto.PasswordResetResponse;
import com.devsh0.chirp.exception.InvalidOldPasswordException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PasswordResetAdvice {
    @ExceptionHandler(InvalidOldPasswordException.class)
    public ResponseEntity<PasswordResetResponse> handleInvalidOldPassword(InvalidOldPasswordException exc) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(PasswordResetResponse.failure(exc.getMessage()));
    }
}
