package com.devsh0.chirp.service.impl;

import com.devsh0.chirp.entity.BlockedJWTToken;
import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.entity.VerificationToken;
import com.devsh0.chirp.exception.*;
import com.devsh0.chirp.other.EmailTemplate;
import com.devsh0.chirp.repository.JWTTokenBlocklistRepository;
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
    private JWTTokenBlocklistRepository blocklistJWTTokenRepository;

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
    public VerificationToken verifyToken(String tokenString) {
        var tokenOrNull = tokenRepository.findByToken(tokenString);
        if (tokenOrNull.isEmpty())
            throw new TokenDoesNotExistException("token does not exist!");
        var token = tokenOrNull.get();
        if (token.hasExpired())
            throw new TokenExpiredException("token expired!");
        return token;
    }

    @Override
    @Transactional
    public void verifyTokenAndActivateAccount(String tokenString) {
        var token = verifyToken(tokenString);
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
    public void logout(HttpServletRequest request) {
        String jwtToken = authenticate(request);
        blocklistJWTTokenRepository.save(BlockedJWTToken.from(jwtToken));
    }

    @Override
    public String authenticate(HttpServletRequest request) {
        String jwtToken = request.getHeader("Authorization");
        if (jwtToken == null || jwtToken.isBlank())
            throw new AuthenticationFailedException("authentication failed!");
        var tokenOrEmpty = blocklistJWTTokenRepository.findByToken(jwtToken);
        if (tokenOrEmpty.isPresent())
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
        // Save the current token to blocklist. Effectively this will log out the user.
        // Fixme: We should block all tokens issued by this user. That will log the user out from every device.
        blocklistJWTTokenRepository.save(BlockedJWTToken.from(jwtToken));
    }

    @Override
    @Transactional
    public void recoverPassword(String email) {
        var userOrEmpty = userRepository.findUserByEmail(email);
        if (userOrEmpty.isEmpty())
            throw new UserDoesNotExistException("no user exists with this email!");
        var user = userOrEmpty.get();
        var verificationToken = createVerificationToken(user);
        sendPasswordRecoveryEmail(user, verificationToken.getToken());
    }

    @Override
    @Transactional
    public void createNewPassword(String password, String tokenString) {
        var token = verifyToken(tokenString);
        Long userId = token.getUserId();
        String encodedPassword = passwordEncoder.encode(password);
        userRepository.resetPassword(userId, encodedPassword);
        tokenRepository.deleteToken(tokenString);
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

    private void sendPasswordRecoveryEmail(User user, String token) {
        // Fixme: The user will click the link in the email to reset password. Clicking the link will make a GET request.
        //  The recovery URL should refer to a page that presents a password reset form. The password changing form in that
        //  page should refer to /api/v1/auth/create-new-password and supply the new password AND the token. We are not sure
        //  what the URL for that page should be, temporarily we are targeting a URL that does not exit. We'll have to come
        //  back to it at some point.
        var recoveryUrl = Utils.getRequestUrl().replace("recover-password", "verify-password-recovery");
        recoveryUrl = recoveryUrl + "?token=" + token;
        var emailTemplate = EmailTemplate.builder()
                .actionHeader("Reset password")
                .actionText("A password reset request was initiated for this email! Follow the link to continue:")
                .linkText("RESET")
                .linkUrl(recoveryUrl).build();
        emailService.sendEmail(user.getEmail(), "Chirp | Password Recovery", emailTemplate.getHtml());
    }

    private void activateAccount(Long userId) {
        userRepository.activateUser(userId);
    }
}
