package com.mockinterview.config.jwt;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mockinterview.dto.response.ErrorResponse;

import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(
        HttpServletRequest request,
        HttpServletResponse response,
        FilterChain filterChain) throws ServletException, IOException {

        String bearerToken = request.getHeader("Authorization");
        String token = jwtProvider.resolveToken(bearerToken);

        if (token == null) {
            sendErrorResponse(response, "토큰이 필요합니다.");
            return;
        }

        try {
            if (!jwtProvider.validateToken(token)) {
                sendErrorResponse(response, "유효하지 않은 토큰입니다.");
                return;
            }

            Long userId = jwtProvider.getUserIdFromToken(token);
            request.setAttribute("userId", userId);

            log.debug("Authenticated user: {}", userId);

            filterChain.doFilter(request, response);
        } catch (ExpiredJwtException e) {
            log.warn("Expired JWT token: {}", e.getMessage());
            sendErrorResponse(response, "만료된 토큰입니다.");
        } catch (Exception e) {
            log.error("JWT token processing error", e);
            sendErrorResponse(response, "토큰 처리 중 오류가 발생했습니다.");
        }
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        String method = request.getMethod();

        return path.startsWith("/api/auth/signup") ||
            path.equals("/api/auth/login") ||
            path.equals("/api/auth/refresh") ||
            path.startsWith("/api/public/") ||
            path.equals("/health") ||
            path.equals("/favicon.ico") ||
            path.startsWith("/swagger-ui/") ||
            path.startsWith("/v3/api-docs") ||
            (path.equals("/") && "GET".equals(method));
    }

    private void sendErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json;charset=UTF-8");

        ErrorResponse errorResponse = new ErrorResponse(
            HttpStatus.UNAUTHORIZED,
            message,
            "JWT_AUTHENTICATION_FAILED"
        );

        String jsonResponse = objectMapper.writeValueAsString(errorResponse);
        response.getWriter().write(jsonResponse);
    }

}
