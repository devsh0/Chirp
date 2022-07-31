package com.devsh0.chirp.dto.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@Getter
public class PasswordRecoveryRequest {
    @NotBlank(message = "email is required!")
    @Email(message = "invalid email!")
    private String email;
}
