package com.devsh0.chirp.dto.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
public class CreateNewPasswordRequest {
    @NotBlank
    @Size(min = 8, message = "password must be at least 8 characters long!")
    private String newPassword;

    @NotNull(message = "token is required!")
    private String token;

    public CreateNewPasswordRequest() {

    }
}
