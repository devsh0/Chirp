package com.devsh0.chirp.dto;

import com.devsh0.chirp.util.Utils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.springframework.validation.BindingResult;
import java.util.Map;

@Getter
@EqualsAndHashCode
public class RegistrationResponse {
    private final boolean success;
    private final Map<String, String> error;

    @JsonCreator
    public RegistrationResponse(@JsonProperty("success") boolean success, @JsonProperty("error") Map<String, String> error) {
        this.success = success;
        this.error = error;
    }

    public static RegistrationResponse success() {
        return new RegistrationResponse(true, Utils.emptyMap());
    }

    public static RegistrationResponse withBindingErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = Utils.emptyMap();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return new RegistrationResponse(false, errorMap);
    }
}
