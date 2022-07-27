package com.devsh0.chirp.service.impl;

import com.devsh0.chirp.dto.RegistrationRequestBody;
import com.devsh0.chirp.dto.RegistrationResponseBody;
import com.devsh0.chirp.entity.User;
import com.devsh0.chirp.repository.UserRepository;
import com.devsh0.chirp.service.AuthenticationService;
import com.devsh0.chirp.util.Utils;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    private boolean emailExists(String email) {
        var userOrError = userRepository.findUserByEmail(email);
        return userOrError.isPresent();
    }
    private boolean usernameExists(String username) {
        var userOrError = userRepository.findUserByUsername(username);
        return userOrError.isPresent();
    }

    @Override
    public RegistrationResponseBody registerUser(RegistrationRequestBody requestBody) {
        if (emailExists(requestBody.getEmail()))
            return new RegistrationResponseBody(false, Utils.mapOf("email", "an account exists with this email!").get());
        if (usernameExists(requestBody.getUsername()))
            return new RegistrationResponseBody(false, Utils.mapOf("username", "username already taken!").get());
        var newUser = new User();
        newUser.setEmail(requestBody.getEmail());
        newUser.setUsername(requestBody.getUsername());
        newUser.setPassword(passwordEncoder.encode(requestBody.getPassword()));
        userRepository.save(newUser);
        return RegistrationResponseBody.success();
    }
}
