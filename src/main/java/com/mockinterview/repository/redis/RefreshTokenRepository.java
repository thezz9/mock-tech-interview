package com.mockinterview.repository.redis;

import java.time.Duration;
import java.util.Date;
import java.util.Optional;

import org.springframework.stereotype.Repository;

import com.mockinterview.config.jwt.JwtProvider;
import com.mockinterview.exception.UserException;
import com.mockinterview.exception.code.UserExceptionCode;

import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class RefreshTokenRepository {

    private final RedisRepository redisRepository;
    private final JwtProvider jwtProvider;

    private static final String PREFIX = "refresh:";

    public void save(Long userId, String refreshToken) {
        String key = PREFIX + userId;

        Date expiration;
        try {
            expiration = jwtProvider.getExpirationDateFromToken(refreshToken);
        } catch (Exception e) {
            throw new UserException(UserExceptionCode.INVALID_REFRESH_TOKEN);
        }

        long ttlMillis = expiration.getTime() - System.currentTimeMillis();

        if (ttlMillis <= 0) {
            throw new UserException(UserExceptionCode.EXPIRED_REFRESH_TOKEN);
        }

        Duration ttl = Duration.ofMillis(ttlMillis);
        redisRepository.save(key, refreshToken, ttl);
    }

    public Optional<String> findByUserId(Long userId) {
        String key = PREFIX + userId;
        return redisRepository.findByKey(key);
    }

    public void deleteByUserId(Long userId) {
        String key = PREFIX + userId;
        redisRepository.deleteByKey(key);
    }
}
