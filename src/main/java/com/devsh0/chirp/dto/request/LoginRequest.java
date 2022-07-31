package com.devsh0.chirp.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginRequest {
    @NotBlank(message = "username or email is required!")
    private String user;
    @NotBlank(message = "password is required!")
    private String password;

    public LoginRequest(String user, String password) {
        this.user = user;
        this.password = password;
    }
}
