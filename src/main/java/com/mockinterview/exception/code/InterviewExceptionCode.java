package com.mockinterview.exception.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum InterviewExceptionCode {

    INTERVIEW_NOT_FOUND(HttpStatus.NOT_FOUND, "인터뷰를 찾을 수 없습니다"),
    INTERVIEW_ACCESS_DENIED(HttpStatus.FORBIDDEN, "해당 인터뷰에 접근할 권한이 없습니다"),
    INTERVIEW_ALREADY_COMPLETED(HttpStatus.BAD_REQUEST, "이미 완료된 인터뷰입니다");

    private final HttpStatus status;
    private final String message;

    InterviewExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
