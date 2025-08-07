package com.mockinterview.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockinterview.dto.request.LoginRequest;
import com.mockinterview.dto.request.RefreshTokenRequest;
import com.mockinterview.dto.request.SignupRequest;
import com.mockinterview.dto.response.LoginResponse;
import com.mockinterview.dto.response.RefreshTokenResponse;
import com.mockinterview.service.AuthService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<Void> signup(@Valid @RequestBody SignupRequest req) {
        authService.signup(req);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .build();
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.login(req));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestAttribute Long userId) {
        authService.logout(userId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refresh(@Valid @RequestBody RefreshTokenRequest req) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(authService.refresh(req));
    }
}
