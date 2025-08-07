package com.mockinterview.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.mockinterview.dto.response.ErrorResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<ErrorResponse> handleException(BaseException e) {
        return ResponseEntity
            .status(e.getStatus())
            .body(new ErrorResponse(
                e.getStatus(),
                e.getMessage(),
                e.getErrorCode()
            ));
    }
}
