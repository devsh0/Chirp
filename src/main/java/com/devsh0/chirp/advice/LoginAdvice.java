package com.devsh0.chirp.advice;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.devsh0.chirp.dto.LoginResponse;
import com.devsh0.chirp.exception.LoginFailedException;
import com.devsh0.chirp.exception.PendingEmailVerificationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class LoginAdvice {

    @ExceptionHandler(LoginFailedException.class)
    public ResponseEntity<LoginResponse> handleLoginException(LoginFailedException exc) {
        var response = LoginResponse.failure(exc.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(PendingEmailVerificationException.class)
    public ResponseEntity<LoginResponse> handleLoginException(PendingEmailVerificationException exc) {
        var response = LoginResponse.failure(exc.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }

    @ExceptionHandler(JWTVerificationException.class)
    public ResponseEntity<LoginResponse> handleTokenVerificationException(JWTVerificationException exc) {
        var response = LoginResponse.failure(exc.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
