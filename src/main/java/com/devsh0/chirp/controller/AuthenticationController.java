package com.devsh0.chirp.controller;

import com.devsh0.chirp.util.JWTTokenUtils;
import com.devsh0.chirp.dto.*;
import com.devsh0.chirp.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(@Valid @RequestBody RegistrationRequest registrationRequest, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(RegistrationResponse.withBindingErrors(bindingResult));
        authenticationService.register(registrationRequest.getEmail(), registrationRequest.getUsername(), registrationRequest.getPassword());
        return ResponseEntity.ok(RegistrationResponse.success());
    }

    @ResponseBody
    @RequestMapping("/verify")
    public ResponseEntity<VerificationResponse> verifyToken(@RequestParam String token) {
        authenticationService.verifyToken(token);
        return ResponseEntity.ok(VerificationResponse.success());
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        var user = authenticationService.login(loginRequest.getUsername(), loginRequest.getPassword());
        var token = JWTTokenUtils.the().generateToken(user);
        return ResponseEntity.ok().body(LoginResponse.success(user.getUsername(), token));
    }

    @PostMapping("/test-login")
    public String testLogin(@RequestHeader("Authorization") String auth) throws Exception {
        String token = auth.substring("Bearer ".length());
        JWTTokenUtils.the().verifyToken(token);
        return "All good";
    }

}
