package com.devsh0.chirp.service.impl;

import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.entity.VerificationToken;
import com.devsh0.chirp.exception.EmailExistsException;
import com.devsh0.chirp.exception.TokenDoesNotExistException;
import com.devsh0.chirp.exception.TokenExpiredException;
import com.devsh0.chirp.exception.UsernameExistsException;
import com.devsh0.chirp.other.EmailTemplate;
import com.devsh0.chirp.repository.UserRepository;
import com.devsh0.chirp.repository.VerificationTokenRepository;
import com.devsh0.chirp.service.AuthenticationService;
import com.devsh0.chirp.service.EmailService;
import com.devsh0.chirp.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;

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
    @Transactional
    public void verifyToken(String tokenString) {
        var token = tokenRepository.findByToken(tokenString);
        if (token == null)
            throw new TokenDoesNotExistException("this token does not exist!");
        if (token.hasExpired())
            throw new TokenExpiredException("token expired!");
        activateAccount(token.getUserId());
    }

    @Override
    public User register(String email, String username, String password) throws IOException {
        if (isEmailExists(email))
            throw new EmailExistsException( "an account exists with this email!");
        if (isUsernameExists(username))
            throw new UsernameExistsException("this username is taken!");

        // Store user in database, create verification token, and send the confirmation email.
        var user = User.builder().email(email).username(username).password(passwordEncoder.encode(password)).build();
        user = userRepository.save(user);
        var verificationToken = createVerificationToken(user);
        sendVerificationEmail(user, verificationToken.getToken());
        return user;
    }

    private void sendVerificationEmail(User user, String token) throws IOException {
        var verificationUrl = Utils.getRequestUrl().replace("register", "verify");
        verificationUrl = verificationUrl + "?token=" + token;
        var emailTemplate = EmailTemplate.builder()
                .actionHeader("Activate your account")
                .actionText("Thanks for signing up with Chirp! You must follow this link to activate your account:")
                .linkText("ACTIVATE")
                .linkUrl(verificationUrl).build();
        emailService.sendEmail(user.getEmail(), "Chirp | Account Activation", emailTemplate.getHtml());
    }

    private void activateAccount(Long userId) {
        userRepository.activateUser(userId);
    }
}
