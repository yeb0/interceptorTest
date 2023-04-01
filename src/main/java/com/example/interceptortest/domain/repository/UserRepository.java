package com.example.interceptortest.domain.repository;

import com.example.interceptortest.domain.user.User;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository // @Repository로 인하여 Spring에서 bean으로 자동 관리
public class UserRepository {
    private static Map<Long, User> userMap = new HashMap<>();
    private static long count = 1L;

    private UserRepository() {}

    public Long save(User user) { // DB 를 따로 사용하지 않기에 auto-increment 역할을 해주는 static count
        user.setId(count);
        userMap.put(count, user);
        count++;
        return user.getId();
    }

    public Boolean isUser(String email, String password) {
        return userMap.values().stream()
                .anyMatch((user -> user.getEmail().equals(email) && user.getPassword().equals(password)));
    }

    public User findByEmail(String email) {
        for (User user : userMap.values()) {
            if (user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public List<User> findAll() {
        return new ArrayList<>(userMap.values());
    }

}
