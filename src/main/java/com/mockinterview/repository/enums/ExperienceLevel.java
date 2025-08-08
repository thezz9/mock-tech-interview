package com.mockinterview.repository.enums;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.mockinterview.exception.UserException;
import com.mockinterview.exception.code.UserExceptionCode;

public enum ExperienceLevel {
    JUNIOR, MIDDLE, SENIOR;

    @JsonCreator
    public static ExperienceLevel from(String value) {
        return Arrays.stream(values())
            .filter(e -> e.name().equalsIgnoreCase(value))
            .findFirst()
            .orElseThrow(() -> new UserException(UserExceptionCode.INVALID_EXPERIENCE_LEVEL));
    }
}
