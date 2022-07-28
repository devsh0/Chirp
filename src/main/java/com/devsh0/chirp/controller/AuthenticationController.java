package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.RegistrationRequestBody;
import com.devsh0.chirp.dto.RegistrationResponseBody;
import com.devsh0.chirp.service.AuthenticationService;
import com.devsh0.chirp.service.EmailService;
import com.devsh0.chirp.util.Utils;
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
    public ResponseEntity<RegistrationResponseBody> register(@Valid @RequestBody RegistrationRequestBody registrationRequestBody, BindingResult bindingResult) throws IOException {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(RegistrationResponseBody.withBindingErrors(bindingResult));
        String report = authenticationService.register(registrationRequestBody.getEmail(), registrationRequestBody.getUsername(), registrationRequestBody.getPassword());
        if (report.contains("email")) {
            var responseBody = new RegistrationResponseBody(false, Utils.mapFrom("email", report).get());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }
        if (report.contains("username")) {
            var responseBody = new RegistrationResponseBody(false, Utils.mapFrom("username", report).get());
            return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
        }

        // Registration went okay, send the confirmation email.
        return ResponseEntity.ok(RegistrationResponseBody.success());
    }
}
