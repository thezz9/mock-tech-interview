package com.mockinterview.dto.request;

import com.mockinterview.repository.enums.ExperienceLevel;
import com.mockinterview.repository.enums.TechField;

public record SignupRequest(
    String email,
    String username,
    String password,
    TechField field,
    ExperienceLevel experience
) {}
