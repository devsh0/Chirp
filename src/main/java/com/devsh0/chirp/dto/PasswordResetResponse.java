package com.devsh0.chirp.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;

@Getter
@EqualsAndHashCode
public class PasswordResetResponse {
    private boolean success;
    private String message;

    public PasswordResetResponse() {

    }

    public PasswordResetResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public PasswordResetResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public static PasswordResetResponse success() {
        return new PasswordResetResponse().setSuccess(true).setMessage("password changed");
    }

    public static PasswordResetResponse failure(String message) {
        return new PasswordResetResponse().setSuccess(false).setMessage(message);
    }

    public static PasswordResetResponse withBindingErrors(BindingResult bindingResult) {
        // There is only one field that we have to care about; the new password field.
        String error = bindingResult.getFieldError("newPassword").getDefaultMessage();
        return new PasswordResetResponse().setSuccess(false).setMessage(error);
    }
}
