package com.mockinterview.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mockinterview.dto.response.interview.InterviewResponse;
import com.mockinterview.service.InterviewService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/interviews")
public class InterviewController {

    private final InterviewService interviewService;

    @PostMapping("/start")
    public ResponseEntity<InterviewResponse> startInterview(@RequestAttribute Long userId) {
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(interviewService.startInterview(userId));
    }

    @PatchMapping("/{interviewId}/complete")
    public ResponseEntity<Void> completeInterview(
        @PathVariable Long interviewId,
        @RequestAttribute Long userId
    ) {
        interviewService.completeInterview(interviewId, userId);
        return ResponseEntity
            .status(HttpStatus.OK)
            .build();
    }
}
