package com.mockinterview.repository.entity;

import java.time.Duration;
import java.time.LocalDateTime;

import com.mockinterview.repository.enums.ExperienceLevel;
import com.mockinterview.repository.enums.InterviewStatus;
import com.mockinterview.repository.enums.TechField;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "interviews")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Interview {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private InterviewStatus status;

    @Column(nullable = false)
    private LocalDateTime startedAt;

    @Column
    private LocalDateTime endedAt;

    @Column
    private Integer durationMinutes;

    @Column
    private Integer totalScore;

    @Column
    private Boolean isPassed;

    @Column(nullable = false)
    private Boolean resultEmailSent;

    @Column
    private LocalDateTime resultEmailSentAt;

    @Builder
    private Interview(
        User user,
        InterviewStatus status,
        LocalDateTime startedAt,
        Boolean resultEmailSent) {
        this.user = user;
        this.status = status;
        this.startedAt = startedAt;
        this.resultEmailSent = resultEmailSent;
    }

    public static Interview create(User user) {
        return Interview.builder()
            .user(user)
            .status(InterviewStatus.IN_PROGRESS)
            .startedAt(LocalDateTime.now())
            .resultEmailSent(false)
            .build();
    }

    public void complete() {
        status = InterviewStatus.COMPLETED;
        endedAt = LocalDateTime.now();
        durationMinutes = (int)Duration.between(startedAt, endedAt).toMinutes();
    }

    public boolean isOwnedBy(Long userId) {
        return user.getId().equals(userId);
    }

    public boolean isCompleted() {
        return status == InterviewStatus.COMPLETED;
    }

    public TechField getField() {
        return user.getField();
    }

    public ExperienceLevel getExperience() {
        return user.getExperience();
    }
}
