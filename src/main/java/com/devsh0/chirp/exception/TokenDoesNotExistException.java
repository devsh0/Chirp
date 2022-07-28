package com.devsh0.chirp.exception;

public class TokenDoesNotExistException extends RuntimeException {
    public TokenDoesNotExistException(String message) {
        super(message);
    }
}
