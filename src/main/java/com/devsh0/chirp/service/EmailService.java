package com.devsh0.chirp.service;

public interface EmailService {
    void sendEmail(String recipient, String subject, String text);
}
