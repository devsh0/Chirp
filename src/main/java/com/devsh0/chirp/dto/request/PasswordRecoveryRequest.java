package com.devsh0.chirp.dto.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.Email;

@Data
@Getter
public class PasswordRecoveryRequest {
    @Email(message = "invalid email!")
    private String email;
}
