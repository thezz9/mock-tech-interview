package com.mockinterview.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.mockinterview.repository.entity.Interview;

public interface InterviewRepository extends JpaRepository<Interview, Long> {
}
