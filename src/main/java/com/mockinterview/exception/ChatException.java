package com.mockinterview.exception;

import org.springframework.http.HttpStatus;

import com.mockinterview.exception.code.ChatExceptionCode;

public class ChatException extends BaseException {

    private final ChatExceptionCode code;

    public ChatException(ChatExceptionCode code) {
        super(code.getMessage());
        this.code = code;
    }

    @Override
    public HttpStatus getStatus() {
        return code.getStatus();
    }

    @Override
    public String getErrorCode() {
        return code.name();
    }
}
