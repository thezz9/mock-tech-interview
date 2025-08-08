package com.mockinterview.dto.response.user;

import com.mockinterview.repository.entity.User;
import com.mockinterview.repository.enums.ExperienceLevel;
import com.mockinterview.repository.enums.TechField;

public record UserResponse(
    String username,
    TechField field,
    ExperienceLevel experience
) {
    public static UserResponse from(User user) {
        return new UserResponse(
            user.getUsername(),
            user.getField(),
            user.getExperience()
        );
    }
}
