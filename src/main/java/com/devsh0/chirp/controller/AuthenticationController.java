package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.request.*;
import com.devsh0.chirp.dto.response.*;
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
    public ResponseEntity<BasicResponse> verifyToken(@RequestParam String token) {
        authenticationService.verifyTokenAndActivateAccount(token);
        return ResponseEntity.ok(BasicResponse.success("token verified"));
    }

    @ResponseBody
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String jwtToken = authenticationService.login(loginRequest.getUser(), loginRequest.getPassword());
        return ResponseEntity.ok().body(LoginResponse.success(loginRequest.getUser(), jwtToken));
    }

    @ResponseBody
    @PostMapping("/reset-password")
    public ResponseEntity<BasicResponse> resetPassword(
            @Valid @RequestBody PasswordResetRequest resetDto,
            BindingResult bindingResult,
            HttpServletRequest httpRequest
    ) {
        if (bindingResult.hasErrors())
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(BasicResponse.withBindingErrors(bindingResult));
        authenticationService.resetPassword(resetDto.getOldPassword(), resetDto.getNewPassword(), httpRequest);
        return ResponseEntity.ok(BasicResponse.success("password successfully reset"));
    }

    @ResponseBody
    @PostMapping("/logout")
    public ResponseEntity<BasicResponse> logout(HttpServletRequest request) {
        authenticationService.logout(request);
        return ResponseEntity.ok().body(BasicResponse.success("logged out successfully"));
    }

    @ResponseBody
    @RequestMapping("/recover-password")
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
    public String testLogin(HttpServletRequest request) {
        authenticationService.authenticate(request);
        return "All good";
    }
}
