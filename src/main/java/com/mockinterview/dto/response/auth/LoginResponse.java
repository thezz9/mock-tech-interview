package com.mockinterview.dto.response.auth;

public record LoginResponse(
    String accessToken,
    String refreshToken,
    String tokenType
) {
}
