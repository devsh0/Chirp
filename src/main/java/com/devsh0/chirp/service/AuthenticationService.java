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
    VerificationToken verifyToken(String token);
    void verifyTokenAndActivateAccount(String token);
    String login(String emailOrUsername, String password);
    void logout(String authToken);
    String authenticate(String authToken);
    void resetPassword(String oldPassword, String newPassword, String authToken);
    void recoverPassword(String email);
    void createNewPassword(String password, String token);
}
