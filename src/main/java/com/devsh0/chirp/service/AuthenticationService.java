package com.devsh0.chirp.service;

import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.entity.VerificationToken;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public interface AuthenticationService {
    boolean isEmailExists(String email);
    boolean isUsernameExists(String username);
    User register(String email, String username, String password) throws IOException;
    VerificationToken createVerificationToken(User user);
    void verifyToken(String token);
    String login(String emailOrUsername, String password);
    void logout(HttpServletRequest request);
    String authenticate(HttpServletRequest request);
    void resetPassword(String oldPassword, String newPassword, HttpServletRequest request);

    void recoverPassword(String email);
}
