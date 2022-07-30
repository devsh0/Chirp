package com.devsh0.chirp.dto;

import lombok.Data;
import org.springframework.validation.BindingResult;

@Data
public class PasswordRecoveryResponse {
    private boolean success;
    private String message;

    public PasswordRecoveryResponse() {

    }

    public PasswordRecoveryResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public PasswordRecoveryResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public static PasswordRecoveryResponse withBindingErrors(BindingResult bindingResult) {
        // We only need to care about the email field.
        String message = bindingResult.getFieldError("email").getDefaultMessage();
        return PasswordRecoveryResponse.failure(message);
    }

    public static PasswordRecoveryResponse success() {
        return new PasswordRecoveryResponse().setSuccess(true).setMessage("password recovery email sent");
    }

    public static PasswordRecoveryResponse failure(String message) {
        return new PasswordRecoveryResponse().setSuccess(false).setMessage(message);
    }
}
