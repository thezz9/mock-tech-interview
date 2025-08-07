package com.mockinterview.service;

import org.springframework.stereotype.Service;

import com.mockinterview.config.PasswordEncoder;
import com.mockinterview.dto.request.SignupRequest;
import com.mockinterview.exception.UserException;
import com.mockinterview.exception.UserExceptionCode;
import com.mockinterview.repository.entity.User;
import com.mockinterview.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void signup(SignupRequest req) {

        if (userRepository.existsByEmail(req.email())) {
            throw new UserException(UserExceptionCode.DUPLICATE_EMAIL);
        }

        String encodedPassword = passwordEncoder.encode(req.password());

        User user = User.createUser(
            req.email(),
            req.username(),
            encodedPassword,
            req.field(),
            req.experience()
        );

        userRepository.save(user);
    }
}
