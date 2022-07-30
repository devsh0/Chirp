package com.devsh0.chirp.dto;

import lombok.Data;
import lombok.Getter;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Getter
public class PasswordResetRequest {
    @NotNull
    @NotBlank
    private String oldPassword;

    @NotNull
    @NotBlank
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String newPassword;

    public PasswordResetRequest() {

    }
}
