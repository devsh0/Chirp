package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.RegistrationRequest;
import com.devsh0.chirp.dto.RegistrationResponse;
import com.devsh0.chirp.dto.VerificationResponse;
import com.devsh0.chirp.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
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
    @PostMapping("/verify")
    public ResponseEntity<VerificationResponse> verifyToken(@RequestParam String token) {
        boolean success = authenticationService.verifyToken(token);
        if (!success)
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(VerificationResponse.failure());
        return ResponseEntity.ok(VerificationResponse.success());
    }
}
