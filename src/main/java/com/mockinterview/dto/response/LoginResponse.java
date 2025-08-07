package com.mockinterview.dto.response;

public record LoginResponse(
    String accessToken,
    String refreshToken,
    String tokenType
) {
}
