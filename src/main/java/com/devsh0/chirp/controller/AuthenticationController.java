package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.request.*;
import com.devsh0.chirp.dto.response.BasicResponse;
import com.devsh0.chirp.dto.response.LoginResponse;
import com.devsh0.chirp.dto.response.RegistrationResponse;
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
    @RequestMapping("/verify")
    public ResponseEntity<BasicResponse> verifyToken(@RequestParam String token) {
        authenticationService.verifyTokenAndActivateAccount(token);
        return ResponseEntity.ok(BasicResponse.success("token verified"));
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        String jwtToken = authenticationService.login(loginRequest.getUser(), loginRequest.getPassword());
        return ResponseEntity.ok().body(LoginResponse.success(loginRequest.getUser(), jwtToken));
    }

    @ResponseBody
    @PostMapping("/reset-password")
    public ResponseEntity<BasicResponse> resetPassword(
            @RequestHeader("Authorization") String authToken,
            @Valid @RequestBody PasswordResetRequest resetDto,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse.withBindingErrors(bindingResult));
        authenticationService.resetPassword(resetDto.getOldPassword(), resetDto.getNewPassword(), authToken);
        return ResponseEntity.ok(BasicResponse.success("password successfully reset"));
    }

    @ResponseBody
    @PostMapping("/logout")
    public ResponseEntity<BasicResponse> logout(@RequestHeader("Authorization") String authToken) {
        authenticationService.logout(authToken);
        return ResponseEntity.ok().body(BasicResponse.success("logged out successfully"));
    }

    @ResponseBody
    @PostMapping("/recover-password")
    public ResponseEntity<BasicResponse> recoverPassword(@Valid @RequestBody PasswordRecoveryRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse.withBindingErrors(bindingResult));
        authenticationService.recoverPassword(request.getEmail());
        return ResponseEntity.ok(BasicResponse.success("password recovery email sent"));
    }

    @ResponseBody
    @PostMapping("/create-new-password")
    public ResponseEntity<BasicResponse> createNewPassword(@Valid @RequestBody CreateNewPasswordRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse.withBindingErrors(bindingResult));
        authenticationService.createNewPassword(request.getNewPassword(), request.getToken());
        return ResponseEntity.ok().body(BasicResponse.success("password successfully reset"));
    }

    @PostMapping("/test-login")
    public String testLogin(@RequestHeader("Authorization") String authToken) {
        authenticationService.authenticate(authToken);
        return "All good";
    }
}
