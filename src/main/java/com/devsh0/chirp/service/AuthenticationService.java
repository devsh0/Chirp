package com.devsh0.chirp.service;

import com.devsh0.chirp.dto.RegistrationRequestBody;

public interface AuthenticationService {
    void registerUser(RegistrationRequestBody body);
}
