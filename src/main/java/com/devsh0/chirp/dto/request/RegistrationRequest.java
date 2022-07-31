package com.devsh0.chirp.dto.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class RegistrationRequest {
    @NotBlank(message = "email is required!")
    @Email(message = "invalid email!")
    private final String email;

    @NotBlank(message = "username is required!")
    @Size(min = 3, max = 30, message = "username must be between 3 to 30 characters long!")
    private final String username;

    @NotBlank(message = "password is required!")
    @Size(min = 8, message = "password must be at least 8 characters long!")
    private final String password;
}
