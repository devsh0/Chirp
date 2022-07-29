package com.devsh0.chirp.exception;

public class PendingEmailVerificationException extends RuntimeException {
    public PendingEmailVerificationException(String message) {
        super(message);
    }
}
