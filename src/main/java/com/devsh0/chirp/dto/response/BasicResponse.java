package com.devsh0.chirp.dto.response;

import lombok.Data;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 * Basic response consists of a simple boolean field signaling whether the request
 * was successful and a message field describing what happened. The "Basic" in BasicResponse
 * comes from the fact that they cannot respond to requests that might have multiple errors since
 * we are limited by just the one message field to contain one and only one error.
 */
@Data
public class BasicResponse {
    private boolean success;
    private String message;

    public BasicResponse() {

    }

    public BasicResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public BasicResponse setMessage(String message) {
        this.message = message;
        return this;
    }

    public static BasicResponse success(String message) {
        return new BasicResponse().setSuccess(true).setMessage(message);
    }

    public static BasicResponse failure(String message) {
        return new BasicResponse().setSuccess(false).setMessage(message);
    }

    // This method expects only a single field error. All other errors, if present, will be ignored.
    public static BasicResponse withBindingErrors(BindingResult bindingResult) {
        FieldError fieldError = bindingResult.getFieldError();
        if (fieldError == null)
            return BasicResponse.failure("the request could not be processed!");
        String error = fieldError.getDefaultMessage();
        return new BasicResponse().setSuccess(false).setMessage(error);
    }
}
