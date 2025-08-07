package com.mockinterview.service;

import org.springframework.stereotype.Service;

import com.mockinterview.dto.request.SignupRequest;
import com.mockinterview.repository.entity.User;
import com.mockinterview.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;

    public void signup(SignupRequest req) {
        User user = User.createUser(
            req.email(),
            req.username(),
            req.password(),
            req.field(),
            req.experience()
        );

        userRepository.save(user);
    }
}
