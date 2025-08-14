package com.mockinterview.exception;

import org.springframework.http.HttpStatus;

import com.mockinterview.exception.code.InterviewExceptionCode;

public class InterviewException extends BaseException {

    private final InterviewExceptionCode code;

    public InterviewException(InterviewExceptionCode code) {
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
