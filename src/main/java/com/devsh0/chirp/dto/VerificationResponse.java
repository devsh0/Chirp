package com.devsh0.chirp.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
public class VerificationResponse {
    private final boolean success;
    private final String message;
    public VerificationResponse(@JsonProperty("success") boolean success, @JsonProperty("message") String message) {
        this.success = success;
        this.message = message;
    }

    public static VerificationResponse success() {
        return new VerificationResponse(true, "token verified");
    }

    public static VerificationResponse failure(String message) {
        return new VerificationResponse(false, message);
    }
}
