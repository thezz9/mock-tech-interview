package com.mockinterview.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mockinterview.config.PasswordEncoder;
import com.mockinterview.dto.request.user.UserDeleteRequest;
import com.mockinterview.dto.request.user.UserUpdatePasswordRequest;
import com.mockinterview.dto.request.user.UserUpdateRequest;
import com.mockinterview.dto.response.user.UserResponse;
import com.mockinterview.exception.UserException;
import com.mockinterview.exception.code.UserExceptionCode;
import com.mockinterview.repository.UserRepository;
import com.mockinterview.repository.entity.User;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public UserResponse getCurrentUserInfo(Long userId) {
        User user = getActiveUserOrThrow(userId);

        return UserResponse.from(user);
    }

    @Transactional
    public UserResponse updateUserInfo(Long userId, UserUpdateRequest req) {
        User user = getActiveUserOrThrow(userId);
        validatePassword(req.password(), user.getPassword());
        user.updateUser(req.username(), req.field(), req.experience());

        return UserResponse.from(user);
    }

    @Transactional
    public void updatePassword(Long userId, UserUpdatePasswordRequest req) {
        User user = getActiveUserOrThrow(userId);
        validatePassword(req.oldPassword(), user.getPassword());

        if (passwordEncoder.matches(req.newPassword(), user.getPassword())) {
            throw new UserException(UserExceptionCode.SAME_AS_OLD_PASSWORD);
        }

        String encodedPassword = passwordEncoder.encode(req.newPassword());
        user.updatePassword(encodedPassword);
    }

    @Transactional
    public void deleteUser(Long userId, UserDeleteRequest req) {
        User user = getActiveUserOrThrow(userId);
        validatePassword(req.password(), user.getPassword());
        user.deleteUser();
    }

    private User getActiveUserOrThrow(Long userId) {
        return userRepository.findById(userId)
            .map(user -> {
                if (!user.getActive()) {
                    throw new UserException(UserExceptionCode.USER_ALREADY_WITHDRAWN);
                }
                return user;
            })
            .orElseThrow(() -> new UserException(UserExceptionCode.USER_NOT_FOUND));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new UserException(UserExceptionCode.INVALID_PASSWORD);
        }
    }
}
