package com.sec.security.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/unsecure")
@RequiredArgsConstructor
public class UnsecuredController {

    @GetMapping("/test")
    public ResponseEntity<String> unsecuredServiceTest() {
        return ResponseEntity.ok().body("this is a test for unsecured API");
    }
}
