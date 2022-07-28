package com.devsh0.chirp.advice;

import com.devsh0.chirp.dto.VerificationResponse;
import com.devsh0.chirp.exception.TokenDoesNotExistException;
import com.devsh0.chirp.exception.TokenExpiredException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TokenVerificationAdvice {

    @ExceptionHandler(TokenDoesNotExistException.class)
    public ResponseEntity<VerificationResponse> handleTokenDoesNotExist(TokenDoesNotExistException exc) {
        var response = VerificationResponse.failure(exc.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<VerificationResponse> handleTokenExpired(TokenExpiredException exc) {
        var response = VerificationResponse.failure(exc.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
