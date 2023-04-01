package com.example.interceptortest.domain;

import com.example.interceptortest.domain.repository.UserRepository;
import com.example.interceptortest.domain.role.Role;
import com.example.interceptortest.domain.user.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class InitData {
    private final UserRepository userRepository;

    @PostConstruct
    public void init() {
        User admin = new User("admin@email.com", "1111", "관리자");
        admin.setRole(Role.ADMIN);
        userRepository.save(admin);

        User hoon = new User("user@email.com", "1234", "사용자");
        hoon.setRole(Role.USER);
        userRepository.save(hoon);

        List<User> users = userRepository.findAll();
        for (User user : users) {
            log.info(user.toString());
        }
    }
}
