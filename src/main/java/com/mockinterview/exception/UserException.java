package com.mockinterview.exception;

import org.springframework.http.HttpStatus;

import com.mockinterview.exception.code.UserExceptionCode;

public class UserException extends BaseException {

    private final UserExceptionCode code;

    public UserException(UserExceptionCode code) {
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
