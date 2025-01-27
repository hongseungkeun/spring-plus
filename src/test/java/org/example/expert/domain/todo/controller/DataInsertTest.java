package org.example.expert.domain.todo.controller;

import org.example.expert.domain.user.entity.User;
import org.example.expert.domain.user.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

@SpringBootTest
public class DataInsertTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void insert() {
        for (int i = 0; i < 1000000; i++) {
            String nickname = UUID.randomUUID().toString();

            User user = new User(null, null, nickname, null);

            userRepository.save(user);
        }
    }

    @Test
    void searchTimeTest() {
        LocalDateTime start = LocalDateTime.now();

        userRepository.findAllByNickname("d3a9280f-e8ec-476c-8762-2cb3893ca6f1");

        LocalDateTime end = LocalDateTime.now();

        Duration duration = Duration.between(start, end);

        System.out.println("소요 시간(ms): " + duration.toMillis() + "ms");
    }
}
