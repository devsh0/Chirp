package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.*;
import com.devsh0.chirp.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
        String jwtToken = authenticationService.login(loginRequest.getUser(), loginRequest.getPassword());
        return ResponseEntity.ok().body(LoginResponse.success(loginRequest.getUser(), jwtToken));
    }

    @ResponseBody
    @PostMapping("/reset-password")
    public ResponseEntity<PasswordResetResponse> resetPassword(
            @Valid @RequestBody PasswordResetRequest resetDto,
            BindingResult bindingResult,
            HttpServletRequest httpRequest
    ) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(PasswordResetResponse.withBindingErrors(bindingResult));
        authenticationService.resetPassword(resetDto.getOldPassword(), resetDto.getNewPassword(), httpRequest);
        return ResponseEntity.ok(PasswordResetResponse.success());
    }

    @ResponseBody
    @PostMapping("/logout")
    public ResponseEntity<LogoutResponse> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok().body(LogoutResponse.success());
    }
    @PostMapping("/test-login")
    public String testLogin(HttpServletRequest request) {
        authenticationService.authenticate(request);
        return "All good";
    }
}
