package com.devsh0.chirp.service;

import com.devsh0.chirp.dto.RegistrationRequestBody;
import com.devsh0.chirp.dto.RegistrationResponseBody;

public interface AuthenticationService {
    RegistrationResponseBody registerUser(RegistrationRequestBody body);
}
