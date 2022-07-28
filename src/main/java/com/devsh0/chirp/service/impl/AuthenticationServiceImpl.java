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

    public boolean isEmailExists(String email) {
        var userOrError = userRepository.findUserByEmail(email);
        return userOrError.isPresent();
    }
    public boolean isUsernameExists(String username) {
        var userOrError = userRepository.findUserByUsername(username);
        return userOrError.isPresent();
    }

    @Override
    public String register(String email, String username, String password) {
        if (isEmailExists(email))
            return "an account exists with this email!";
        if (isUsernameExists(username))
            return "username already taken!";
        var newUser = new User();
        newUser.setEmail(email);
        newUser.setUsername(username);
        newUser.setPassword(passwordEncoder.encode(password));
        return userRepository.save(newUser).getUsername();
    }
}
