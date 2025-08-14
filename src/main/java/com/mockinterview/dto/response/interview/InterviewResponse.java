package com.mockinterview.dto.response.interview;

import java.time.LocalDateTime;

import com.mockinterview.repository.entity.Interview;
import com.mockinterview.repository.enums.ExperienceLevel;
import com.mockinterview.repository.enums.InterviewStatus;
import com.mockinterview.repository.enums.TechField;

public record InterviewResponse(
    Long interviewId,
    TechField field,
    ExperienceLevel experience,
    InterviewStatus status,
    LocalDateTime startedAt
) {
    public static InterviewResponse from(Interview interview) {
        return new InterviewResponse(
            interview.getId(),
            interview.getField(),
            interview.getExperience(),
            interview.getStatus(),
            interview.getStartedAt()
        );
    }
}
