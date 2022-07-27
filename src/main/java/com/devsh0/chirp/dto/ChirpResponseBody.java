package com.devsh0.chirp.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode
public class ChirpResponseBody {
    private final boolean success;

    @JsonCreator
    public ChirpResponseBody(@JsonProperty("success") boolean success) {
        this.success = success;
    }
}
