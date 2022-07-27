package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.RegistrationRequestBody;
import com.devsh0.chirp.dto.RegistrationResponseBody;
import com.devsh0.chirp.service.AuthenticationService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthenticationController {
    private AuthenticationService authenticationService;

    @ResponseBody
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponseBody> register(@Valid @RequestBody RegistrationRequestBody registrationRequestBody, BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return ResponseEntity.badRequest().body(RegistrationResponseBody.withBindingErrors(bindingResult));
        var responseBody = authenticationService.registerUser(registrationRequestBody);
        var httpStatus = responseBody.getError().isEmpty() ? HttpStatus.OK : HttpStatus.CONFLICT;
        return ResponseEntity.status(httpStatus).body(responseBody);
    }
}
