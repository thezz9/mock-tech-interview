package com.mockinterview.dto.request.auth;

import com.mockinterview.repository.enums.ExperienceLevel;
import com.mockinterview.repository.enums.TechField;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record SignupRequest(

    @NotBlank(message = "이메일은 필수 입력 값입니다.")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
        message = "올바른 이메일 형식이 아닙니다."
    )
    @Size(max = 100, message = "이메일은 100자를 초과할 수 없습니다.")
    String email,

    @NotBlank(message = "이름은 필수 입력 값입니다.")
    @Size(max = 30, message = "이름은 30자를 초과할 수 없습니다.")
    String username,

    @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
    @Size(min = 8, max = 100, message = "비밀번호는 8자 이상 100자 이하여야 합니다.")
    @Pattern(
        regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[a-zA-Z\\d@$!%*?&]{8,}$",
        message = "비밀번호는 영문, 숫자, 특수문자(@$!%*?&)를 각각 하나 이상 포함해야 합니다."
    )
    String password,

    @NotNull(message = "기술 분야를 선택해주세요.")
    TechField field,

    @NotNull(message = "경력 수준을 선택해주세요.")
    ExperienceLevel experience
) {
}
