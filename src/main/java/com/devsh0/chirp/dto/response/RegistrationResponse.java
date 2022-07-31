package com.devsh0.chirp.dto.response;

import com.devsh0.chirp.util.Utils;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.Map;

@Getter
@EqualsAndHashCode
public class RegistrationResponse {
    private boolean success;
    private Map<String, String> error;
    public RegistrationResponse() {

    }

    public RegistrationResponse setSuccess(boolean success) {
        this.success = success;
        return this;
    }

    public RegistrationResponse setError(Map<String, String> error) {
        this.error = error;
        return this;
    }

    public static RegistrationResponse success() {
        return new RegistrationResponse().setSuccess(true).setError(Utils.emptyMap());
    }

    public static RegistrationResponse withBindingErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = Utils.emptyMap();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return new RegistrationResponse().setSuccess(false).setError(errorMap);
    }
}
