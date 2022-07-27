package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.RegistrationRequestBody;
import com.devsh0.chirp.dto.RegistrationResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/auth")
public class AuthorizationController {
    private static final Logger logger = LoggerFactory.getLogger(AuthorizationController.class);

    @PostMapping("/register")
    @ResponseBody
    public ResponseEntity<RegistrationResponseBody> register(@Valid @RequestBody RegistrationRequestBody registrationRequestBody, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(RegistrationResponseBody.withBindingErrors(bindingResult));
        return ResponseEntity.ok(RegistrationResponseBody.success());
    }
}
