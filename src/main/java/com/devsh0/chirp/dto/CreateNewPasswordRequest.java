package com.devsh0.chirp.dto;

import lombok.Data;

import javax.validation.constraints.Size;

@Data
public class CreateNewPasswordRequest {
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String newPassword;
    private String token;

    public CreateNewPasswordRequest() {

    }
}
