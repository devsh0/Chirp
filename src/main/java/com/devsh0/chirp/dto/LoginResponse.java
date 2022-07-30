package com.devsh0.chirp.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class LoginResponse {
    private boolean success;
    private String message;
    private String user;
    private String token = "";

    public LoginResponse() {

    }

    public LoginResponse(boolean success, String user, String token, String message) {
        this.success = success;
        this.user = user;
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
