package com.devsh0.chirp.service.impl;

import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.repository.UserRepository;
import com.devsh0.chirp.service.AuthenticationService;
import com.devsh0.chirp.service.EmailService;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;
    private final UserRepository userRepository;

    public boolean isEmailExists(String email) {
        var userOrError = userRepository.findUserByEmail(email);
        return userOrError.isPresent();
    }
    public boolean isUsernameExists(String username) {
        var userOrError = userRepository.findUserByUsername(username);
        return userOrError.isPresent();
    }
    private void sendConfirmationEmail(String recipient) {
        String text = "Confirm your account by clicking the following link.";
        emailService.sendEmail(recipient, "Chirp: Email Confirmation", text);
    }
    @Override
    public String register(String email, String username, String password) {
        if (isEmailExists(email))
            return "an account exists with this email!";
        if (isUsernameExists(username))
            return "username already taken!";

        // Store user in database and send the confirmation email.
        var user = User.builder().email(email).username(username).password(passwordEncoder.encode(password)).build();
        userRepository.save(user);
        sendConfirmationEmail(user.getEmail());
        return user.getUsername();
    }
}
