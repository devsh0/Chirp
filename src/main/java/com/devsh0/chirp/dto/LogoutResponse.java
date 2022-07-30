package com.devsh0.chirp.dto;

import lombok.Data;

@Data
public class LogoutResponse {
    private boolean success;
    private String message;

    public LogoutResponse() {

    }

    public LogoutResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public LogoutResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public static LogoutResponse success() {
        return new LogoutResponse().setSuccess(true).setMessage("");
    }

    public static LogoutResponse failure(String message) {
        return new LogoutResponse().setSuccess(true).setMessage(message);
    }
}
