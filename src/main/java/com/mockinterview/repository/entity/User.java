package com.mockinterview.repository.entity;

import com.mockinterview.config.BaseEntity;
import com.mockinterview.repository.enums.ExperienceLevel;
import com.mockinterview.repository.enums.TechField;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false, length = 30)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private TechField field;

    @Column(nullable = false, length = 20)
    @Enumerated(EnumType.STRING)
    private ExperienceLevel experience;

    @Column(nullable = false)
    private Boolean active;

    public static User createUser(
        String email,
        String username,
        String password,
        TechField field,
        ExperienceLevel experience
    ) {
        User user = new User();
        user.email = email;
        user.username = username;
        user.password = password;
        user.field = field;
        user.experience = experience;
        user.active = true;
        return user;
    }
}
