package com.devsh0.chirp.advice;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.devsh0.chirp.exception.AuthenticationFailedException;
import com.devsh0.chirp.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Map;

@ControllerAdvice
public class AuthenticationAdvice {
    @ExceptionHandler({AuthenticationFailedException.class, JWTVerificationException.class})
    public ResponseEntity<Map<String, Object>> handleAuthenticationFailed(RuntimeException exc) {
        var response = Utils.mapFrom("success", (Object) false).put("message", exc.getMessage()).get();
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
    }
}
