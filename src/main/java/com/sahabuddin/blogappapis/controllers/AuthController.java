package com.sahabuddin.blogappapis.controllers;

import com.sahabuddin.blogappapis.payloads.SignInRequest;
import com.sahabuddin.blogappapis.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api")
public class AuthController {

    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<String> login(@Valid @RequestBody SignInRequest request) {
        String token = userService.verify(request);
        return ResponseEntity.ok("token: "+token);
    }
}
