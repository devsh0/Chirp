package com.devsh0.chirp.service;

import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.entity.VerificationToken;

public interface AuthenticationService {
    boolean isEmailExists(String email);
    boolean isUsernameExists(String username);
    String register(String email, String username, String password);
    VerificationToken createVerificationToken(User user);
}
