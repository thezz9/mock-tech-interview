package com.mockinterview.exception.code;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public enum ChatExceptionCode {

    QUESTION_NOT_FOUND(HttpStatus.NOT_FOUND, "질문을 찾을 수 없습니다"),
    ANSWER_NOT_FOUND(HttpStatus.NOT_FOUND, "답변을 찾을 수 없습니다");

    private final HttpStatus status;
    private final String message;

    ChatExceptionCode(HttpStatus status, String message) {
        this.status = status;
        this.message = message;
    }
}
