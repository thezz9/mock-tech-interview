package com.mockinterview.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mockinterview.dto.QuestionMessage;
import com.mockinterview.exception.InterviewException;
import com.mockinterview.exception.code.InterviewExceptionCode;
import com.mockinterview.repository.ChatRepository;
import com.mockinterview.repository.InterviewRepository;
import com.mockinterview.repository.entity.Chat;
import com.mockinterview.repository.entity.Interview;
import com.mockinterview.repository.enums.ChatType;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final InterviewRepository interviewRepository;
    private final ChatRepository chatRepository;

    public QuestionMessage ensureFirstQuestion(Long interviewId, Long userId) {

        Interview interview = interviewRepository.findById(interviewId)
            .orElseThrow(() -> new InterviewException(InterviewExceptionCode.INTERVIEW_NOT_FOUND));

        if (!interview.isOwnedBy(userId)) {
            throw new InterviewException(InterviewExceptionCode.INTERVIEW_ACCESS_DENIED);
        }

        if (interview.isCompleted()) {
            throw new InterviewException(InterviewExceptionCode.INTERVIEW_ALREADY_COMPLETED);
        }

        Optional<Chat> chat = chatRepository.findByInterviewIdAndTurnAndType(interviewId, 0,
            ChatType.QUESTION);

        String question = chat.map(Chat::getMessage).orElseGet(() -> {
            String q = buildFirstQuestion(interview);
            chatRepository.save(Chat.createQuestion(interview, q, 0));
            return q;
        });

        return new QuestionMessage(interviewId, 0, question);
    }

    private String buildFirstQuestion(Interview interview) {
        return "자기소개와 최근에 해결한 기술적 문제를 하나 설명해 주세요.";
    }
}
