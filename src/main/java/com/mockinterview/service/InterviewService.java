package com.mockinterview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mockinterview.dto.response.interview.InterviewResponse;
import com.mockinterview.exception.InterviewException;
import com.mockinterview.exception.UserException;
import com.mockinterview.exception.code.InterviewExceptionCode;
import com.mockinterview.exception.code.UserExceptionCode;
import com.mockinterview.repository.InterviewRepository;
import com.mockinterview.repository.UserRepository;
import com.mockinterview.repository.entity.Interview;
import com.mockinterview.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class InterviewService {

    private final UserRepository userRepository;
    private final InterviewRepository interviewRepository;

    @Transactional
    public InterviewResponse startInterview(Long userId) {

        User user = userRepository.findById(userId)
            .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));

        Interview interview = interviewRepository.save(Interview.create(user));

        return InterviewResponse.from(interview);
    }

    @Transactional
    public void completeInterview(Long interviewId, Long userId) {

        Interview interview = interviewRepository.findById(interviewId)
            .orElseThrow(() -> new InterviewException(InterviewExceptionCode.INTERVIEW_NOT_FOUND));

        if (!interview.isOwnedBy(userId)) {
            throw new InterviewException(InterviewExceptionCode.INTERVIEW_ACCESS_DENIED);
        }

        if (interview.isCompleted()) {
            throw new InterviewException(InterviewExceptionCode.INTERVIEW_ALREADY_COMPLETED);
        }

        interview.complete();
    }
}
