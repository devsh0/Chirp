package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.ChirpResponseBody;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @RequestMapping("/test")
    public ResponseEntity<ChirpResponseBody> test() {
        return ResponseEntity.ok(new ChirpResponseBody(true));
    }
}
