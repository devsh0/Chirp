package com.devsh0.chirp.controller;

import com.devsh0.chirp.dto.response.BasicResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class TestController {

    @RequestMapping("/test")
    public ResponseEntity<BasicResponse> test() {
        return ResponseEntity.ok(BasicResponse.success("all good"));
    }
}
