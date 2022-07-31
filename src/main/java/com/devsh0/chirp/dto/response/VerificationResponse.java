package com.devsh0.chirp.dto.response;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class VerificationResponse {
    private boolean success;
    private String message;

    public VerificationResponse() {

    }

    public VerificationResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public VerificationResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public static VerificationResponse success() {
        return new VerificationResponse().setSuccess(true).setMessage("token verified");
    }

    public static VerificationResponse failure(String message) {
        return new VerificationResponse().setSuccess(false).setMessage(message);
    }
}
