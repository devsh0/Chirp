package com.devsh0.chirp.service.impl;

import com.devsh0.chirp.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailServiceImpl implements EmailService {
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String recipient, String subject, String text) {
        var message = new SimpleMailMessage();
        message.setFrom("test@chirp.com");
        message.setTo(recipient);
        message.setSubject(subject);
        message.setText(text);
        mailSender.send(message);
    }
}
