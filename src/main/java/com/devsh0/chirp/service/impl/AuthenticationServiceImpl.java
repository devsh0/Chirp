package com.devsh0.chirp.service.impl;

import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.entity.VerificationToken;
import com.devsh0.chirp.exception.*;
import com.devsh0.chirp.other.EmailTemplate;
import com.devsh0.chirp.repository.UserRepository;
import com.devsh0.chirp.repository.VerificationTokenRepository;
import com.devsh0.chirp.service.AuthenticationService;
import com.devsh0.chirp.service.EmailService;
import com.devsh0.chirp.util.JWTTokenUtils;
import com.devsh0.chirp.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import javax.servlet.http.HttpServletRequest;

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
            throw new TokenDoesNotExistException("token does not exist!");
        if (token.hasExpired())
            throw new TokenExpiredException("token expired!");
        activateAccount(token.getUserId());
    }

    @Override
    public String login(String emailOrUsername, String password) {
        User user = userRepository.findByEmailOrUsername(emailOrUsername).orElseThrow(
                () -> new LoginFailedException("invalid credentials!"));
        if (!user.isActive())
            throw new PendingEmailVerificationException("email verification pending!");
        if (!passwordEncoder.matches(password, user.getPassword()))
            throw new LoginFailedException("invalid credentials!");
        return JWTTokenUtils.the().generateToken(user);
    }

    @Override
    public String authenticate(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null || jwtToken.isBlank())
            throw new AuthenticationFailedException("authentication failed!");
        String token = jwtToken.replace("Bearer ", "");
        JWTTokenUtils.the().verifyToken(token);
        return token;
    }

    @Override
    @Transactional
    public User register(String email, String username, String password) {
        if (isEmailExists(email))
            throw new EmailExistsException("email already in use!");
        if (isUsernameExists(username))
            throw new UsernameExistsException("username already in use!");

        // Store user in database, create verification token, and send the confirmation email.
        var user = User.builder().email(email).username(username).password(passwordEncoder.encode(password)).build();
        user = userRepository.save(user);
        var verificationToken = createVerificationToken(user);
        sendVerificationEmail(user, verificationToken.getToken());
        return user;
    }

    @Override
    @Transactional
    public void resetPassword(String oldPassword, String newPassword, HttpServletRequest request) {
        String jwtToken = authenticate(request);
        String username = JWTTokenUtils.the().getSubject(jwtToken);
        var userOrEmpty = userRepository.findUserByUsername(username);

        if (userOrEmpty.isEmpty())
            throw new IllegalStateException("password reset requested by unknown user!");
        var user = userOrEmpty.get();
        if (!passwordEncoder.matches(oldPassword, user.getPassword()))
            throw new InvalidOldPasswordException("old password is invalid!");

        String encodedPassword = passwordEncoder.encode(newPassword);
        userRepository.resetPassword(user.getId(), encodedPassword);
        // Fixme: Maybe we should log the user out after password change.
    }

    private void sendVerificationEmail(User user, String token) {
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
