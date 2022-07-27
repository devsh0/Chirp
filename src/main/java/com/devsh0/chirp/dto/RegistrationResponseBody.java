package com.devsh0.chirp.dto;

import com.devsh0.chirp.util.Utils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.validation.BindingResult;

import java.util.Map;
import java.util.Objects;

@Getter
public class RegistrationResponseBody extends ChirpResponseBody {
    private final Map<String, String> error;

    @JsonCreator
    public RegistrationResponseBody(@JsonProperty("success") boolean success, @JsonProperty("error") Map<String, String> error) {
        super(success);
        this.error = error;
    }

    public static RegistrationResponseBody success() {
        return new RegistrationResponseBody(true, Utils.emptyMap());
    }

    public static RegistrationResponseBody withBindingErrors(BindingResult bindingResult) {
        Map<String, String> errorMap = Utils.emptyMap();
        bindingResult.getFieldErrors().forEach(fieldError -> errorMap.put(fieldError.getField(), fieldError.getDefaultMessage()));
        return new RegistrationResponseBody(false, errorMap);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other)
            return true;
        if (other == null || getClass() != other.getClass())
            return false;
        if (!super.equals(other))
            return false;
        RegistrationResponseBody that = (RegistrationResponseBody) other;
        return error.equals(that.error);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), error);
    }
}
