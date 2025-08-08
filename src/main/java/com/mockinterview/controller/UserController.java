package com.mockinterview.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockinterview.dto.request.user.UserDeleteRequest;
import com.mockinterview.dto.request.user.UserUpdatePasswordRequest;
import com.mockinterview.dto.request.user.UserUpdateRequest;
import com.mockinterview.dto.response.user.UserResponse;
import com.mockinterview.service.UserService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/me")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserResponse> getCurrentUserInfo(@RequestAttribute Long userId) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.getCurrentUserInfo(userId));
    }

    @PatchMapping
    public ResponseEntity<UserResponse> updateUserInfo(
        @RequestAttribute Long userId,
        @Valid @RequestBody UserUpdateRequest req
    ) {
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(userService.updateUserInfo(userId, req));
    }

    @PatchMapping("/password")
    public ResponseEntity<Void> updatePassword(
        @RequestAttribute Long userId,
        @Valid @RequestBody UserUpdatePasswordRequest req
    ) {
        userService.updatePassword(userId, req);
        return ResponseEntity
            .ok()
            .build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteUser(
        @RequestAttribute Long userId,
        @Valid @RequestBody UserDeleteRequest req
    ) {
        userService.deleteUser(userId, req);
        return ResponseEntity
            .noContent()
            .build();
    }
}
