package com.mockinterview.repository.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mockinterview.exception.UserException;
import com.mockinterview.exception.code.UserExceptionCode;

public enum TechField {
    FRONTEND, BACKEND;

    @JsonCreator
    public static TechField from(String value) {
        return Arrays.stream(values())
            .filter(e -> e.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new UserException(UserExceptionCode.INVALID_TECH_FIELD));
    }
}
