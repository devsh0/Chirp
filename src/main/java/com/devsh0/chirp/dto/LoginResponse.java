package com.devsh0.chirp.dto;

import com.devsh0.chirp.entity.User;
import lombok.Data;

@Data
public class LoginResponse {
    private boolean success;
    private String message;
    private String username;
    private String token = "";

    public LoginResponse() {

    }

    public LoginResponse(boolean success, String username, String token, String message) {
        this.success = success;
        this.username = username;
        this.token = token;
        this.message = message;
    }

    public static LoginResponse success(String username, String token) {
        return new LoginResponse(true, username, token, "login success");
    }

    public static LoginResponse failure(String message) {
        return new LoginResponse(false, null, null, message);
    }
}
