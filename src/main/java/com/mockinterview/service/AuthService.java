package com.mockinterview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mockinterview.config.PasswordEncoder;
import com.mockinterview.config.jwt.JwtProvider;
import com.mockinterview.dto.request.auth.LoginRequest;
import com.mockinterview.dto.request.auth.RefreshTokenRequest;
import com.mockinterview.dto.request.auth.SignupRequest;
import com.mockinterview.dto.response.auth.LoginResponse;
import com.mockinterview.dto.response.auth.RefreshTokenResponse;
import com.mockinterview.exception.UserException;
import com.mockinterview.exception.code.UserExceptionCode;
import com.mockinterview.repository.entity.User;
import com.mockinterview.repository.UserRepository;
import com.mockinterview.repository.redis.RefreshTokenRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public void signup(SignupRequest req) {

        userRepository.findByEmail(req.email())
            .ifPresent(user -> {
                if (user.getActive()) {
                    throw new UserException(UserExceptionCode.DUPLICATE_EMAIL);
                } else {
                    throw new UserException(UserExceptionCode.USER_ALREADY_WITHDRAWN);
                }
            });

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

    @Transactional
    public LoginResponse login(LoginRequest req) {

        User user = userRepository.findByEmail(req.email())
            .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        if (!user.getActive()) {
            throw new UserException(UserExceptionCode.USER_ALREADY_WITHDRAWN);
        }

        if (!passwordEncoder.matches(req.password(), user.getPassword())) {
            throw new UserException(UserExceptionCode.INVALID_CREDENTIALS);
        }

        Long userId = user.getId();

        String accessToken = jwtProvider.generateAccessToken(userId);
        String refreshToken = jwtProvider.generateRefreshToken(userId);

        refreshTokenRepository.save(userId, refreshToken);

        return new LoginResponse(accessToken, refreshToken, "Bearer");
    }

    @Transactional
    public void logout(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }

    @Transactional(readOnly = true)
    public RefreshTokenResponse refresh(RefreshTokenRequest req) {
        String refreshToken = req.refreshToken();

        if (!jwtProvider.validateToken(refreshToken)) {
            throw new UserException(UserExceptionCode.INVALID_REFRESH_TOKEN);
        }

        Long userId = jwtProvider.getUserIdFromToken(refreshToken);

        String storedToken = refreshTokenRepository.findByUserId(userId)
            .orElseThrow(() -> new UserException(UserExceptionCode.EXPIRED_REFRESH_TOKEN));

        if (!storedToken.equals(refreshToken)) {
            throw new UserException(UserExceptionCode.INVALID_REFRESH_TOKEN);
        }

        String newAccessToken = jwtProvider.generateAccessToken(userId);
        return new RefreshTokenResponse(newAccessToken);
    }
}
