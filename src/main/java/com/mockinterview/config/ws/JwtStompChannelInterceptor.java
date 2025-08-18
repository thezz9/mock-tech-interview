package com.mockinterview.config.ws;

import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.stereotype.Component;

import com.mockinterview.config.jwt.JwtProvider;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class JwtStompChannelInterceptor implements ChannelInterceptor {

    private final JwtProvider jwtProvider;

    @Override
    public Message<?> preSend(
        @NonNull Message<?> message,
        @NonNull MessageChannel channel
    ) {
        StompHeaderAccessor acc = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (acc == null || acc.getCommand() == null) return message;

        if (acc.getCommand() == StompCommand.CONNECT) {
            String auth = acc.getFirstNativeHeader("Authorization");
            String token = jwtProvider.resolveToken(auth);
            if (token == null || !jwtProvider.validateToken(token)) {
                throw new MessagingException("Invalid or missing JWT");
            }
            Long userId = jwtProvider.getUserIdFromToken(token);

            acc.setUser(() -> String.valueOf(userId));
        }

        return message;
    }
}
