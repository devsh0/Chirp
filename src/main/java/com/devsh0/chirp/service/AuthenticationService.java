package com.devsh0.chirp.service;

public interface AuthenticationService {
    boolean isEmailExists(String email);
    boolean isUsernameExists(String username);
    String register(String email, String username, String password);
}
