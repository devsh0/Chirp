package com.devsh0.chirp.dto.request;

import lombok.Data;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Getter
public class PasswordResetRequest {
    @NotBlank(message = "old password is required!")
    private String oldPassword;

    @NotBlank(message = "new password is required!")
    @Size(min = 8, message = "Password must be at least 8 characters long!")
    private String newPassword;

    public PasswordResetRequest() {

    }
}
