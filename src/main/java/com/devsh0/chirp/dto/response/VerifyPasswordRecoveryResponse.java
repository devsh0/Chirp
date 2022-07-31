package com.devsh0.chirp.dto.response;

import lombok.Data;

@Data
public class VerifyPasswordRecoveryResponse {
    private boolean success;
    private String message;

    public VerifyPasswordRecoveryResponse() {

    }

    public VerifyPasswordRecoveryResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public VerifyPasswordRecoveryResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public static VerifyPasswordRecoveryResponse success() {
        return new VerifyPasswordRecoveryResponse().setSuccess(true).setMessage("all good");
    }

    public static VerifyPasswordRecoveryResponse failure(String message) {
        return new VerifyPasswordRecoveryResponse().setSuccess(false).setMessage(message);
    }
}
