package com.devsh0.chirp.advice;

import com.devsh0.chirp.dto.RegistrationResponseBody;
import com.devsh0.chirp.exception.EmailExistsException;
import com.devsh0.chirp.exception.UsernameExistsException;
import com.devsh0.chirp.util.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class RegistrationAdvice {
    @ExceptionHandler(EmailExistsException.class)
    ResponseEntity<RegistrationResponseBody> handleEmailExists(EmailExistsException exc) {
        var responseBody = new RegistrationResponseBody(false, Utils.mapFrom("email", exc.getMessage()).get());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
    }

    @ExceptionHandler(UsernameExistsException.class)
    ResponseEntity<RegistrationResponseBody> handleUsernameExists(UsernameExistsException exc) {
        var responseBody = new RegistrationResponseBody(false, Utils.mapFrom("username", exc.getMessage()).get());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(responseBody);
    }
}
