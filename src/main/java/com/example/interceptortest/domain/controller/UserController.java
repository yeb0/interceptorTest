package com.example.interceptortest.domain.controller;

import com.example.interceptortest.domain.config.MyAnnotation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @MyAnnotation
    @GetMapping("/user")
    public String user() {
        return "사용자 페이지입니다. !";
    }
}
