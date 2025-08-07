package com.mockinterview.exception;

import org.springframework.http.HttpStatus;

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
