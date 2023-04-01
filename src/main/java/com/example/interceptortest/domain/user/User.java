package com.example.interceptortest.domain.user;

import com.example.interceptortest.domain.role.Role;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class User {
    private Long id;
    private String email;
    private String password;
    private String name;
    private Role role;

    public User() {}

    public User(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }
}
