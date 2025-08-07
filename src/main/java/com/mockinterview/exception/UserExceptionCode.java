package com.mockinterview.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum UserExceptionCode {

    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "사용자를 찾을 수 없습니다"),
    DUPLICATE_EMAIL(HttpStatus.BAD_REQUEST, "이미 존재하는 이메일입니다"),
    INVALID_CREDENTIALS(HttpStatus.UNAUTHORIZED, "이메일 또는 비밀번호가 틀렸습니다"),
    INVALID_PASSWORD_FORMAT(HttpStatus.BAD_REQUEST, "비밀번호 형식이 올바르지 않습니다");

    private final HttpStatus status;
    private final String message;

    UserExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
