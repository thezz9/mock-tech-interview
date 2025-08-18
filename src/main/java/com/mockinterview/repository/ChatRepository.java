package com.mockinterview.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mockinterview.repository.entity.Chat;
import com.mockinterview.repository.enums.ChatType;

public interface ChatRepository extends JpaRepository<Chat, Long> {
    Optional<Chat> findByInterviewIdAndTurnAndType(Long interviewId, Integer turn, ChatType type);
}
