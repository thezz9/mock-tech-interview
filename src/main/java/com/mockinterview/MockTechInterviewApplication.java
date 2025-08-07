package com.mockinterview;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MockTechInterviewApplication {

    public static void main(String[] args) {
        SpringApplication.run(MockTechInterviewApplication.class, args);
    }

}
