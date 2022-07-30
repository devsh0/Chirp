package com.devsh0.chirp.advice;

import com.devsh0.chirp.dto.PasswordRecoveryResponse;
import com.devsh0.chirp.exception.UserDoesNotExistException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class PasswordRecoveryAdvice {
    @ExceptionHandler(UserDoesNotExistException.class)
    public ResponseEntity<PasswordRecoveryResponse> handleUserDoesNotExist(UserDoesNotExistException exc) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(PasswordRecoveryResponse.failure(exc.getMessage()));
    }
}
