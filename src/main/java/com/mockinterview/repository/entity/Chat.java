package com.mockinterview.repository.entity;

import java.time.LocalDateTime;

import com.mockinterview.repository.enums.ChatType;

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
@Table(name = "chats")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "interview_id", nullable = false)
    private Interview interview;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChatType type;

    @Column(nullable = false)
    private Integer turn;

    @Column
    private Integer score;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Builder
    private Chat(
        Interview interview,
        String message,
        ChatType type,
        Integer turn
    ) {
        this.interview = interview;
        this.message = message;
        this.type = type;
        this.turn = turn;
        createdAt = LocalDateTime.now();
    }

    public static Chat createQuestion(Interview interview, String question, int turn) {
        return Chat.builder()
            .interview(interview)
            .message(question)
            .type(ChatType.QUESTION)
            .turn(turn)
            .build();
    }

    public static Chat createAnswer(Interview interview, String answer, int turn) {
        return Chat.builder()
            .interview(interview)
            .message(answer)
            .type(ChatType.ANSWER)
            .turn(turn)
            .build();
    }

    public void updateScore(Integer score) {
        this.score = score;
    }
}
