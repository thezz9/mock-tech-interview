package com.mockinterview.controller;

import java.security.Principal;

import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import com.mockinterview.dto.QuestionMessage;
import com.mockinterview.service.ChatService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class InterviewWsController {

    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/interviews/{interviewId}/ready")
    public void ready(
        @DestinationVariable Long interviewId,
        Principal principal
    ) {

        String userIdStr = principal.getName();
        Long userId = Long.valueOf(userIdStr);

        QuestionMessage question = chatService.ensureFirstQuestion(interviewId, userId);

        String destination = "/queue/interview";
        simpMessagingTemplate.convertAndSendToUser(userIdStr, destination, question);
    }
}