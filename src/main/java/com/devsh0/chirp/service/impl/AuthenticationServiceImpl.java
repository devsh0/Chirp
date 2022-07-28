package com.devsh0.chirp.service.impl;

import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.entity.VerificationToken;
import com.devsh0.chirp.repository.UserRepository;
import com.devsh0.chirp.repository.VerificationTokenRepository;
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
    private final VerificationTokenRepository tokenRepository;

    @Override
    public boolean isEmailExists(String email) {
        var userOrError = userRepository.findUserByEmail(email);
        return userOrError.isPresent();
    }

    @Override
    public boolean isUsernameExists(String username) {
        var userOrError = userRepository.findUserByUsername(username);
        return userOrError.isPresent();
    }

    @Override
    public VerificationToken createVerificationToken(User user) {
        var token = VerificationToken.generateToken(user);
        tokenRepository.save(token);
        return token;
    }

    @Override
    public String register(String email, String username, String password) {
        if (isEmailExists(email))
            return "an account exists with this email!";
        if (isUsernameExists(username))
            return "username already taken!";

        // Store user in database, create verification token, and send the confirmation email.
        var user = User.builder().email(email).username(username).password(passwordEncoder.encode(password)).build();
        user = userRepository.save(user);
        var verificationToken = createVerificationToken(user);
        sendConfirmationEmail(user.getEmail(), verificationToken.getToken());
        return user.getUsername();
    }

    private void sendConfirmationEmail(String recipient, String token) {
        String text = String.format("Confirm your account using this token: %s", token);
        emailService.sendEmail(recipient, "Chirp: Email Confirmation", text);
    }
}