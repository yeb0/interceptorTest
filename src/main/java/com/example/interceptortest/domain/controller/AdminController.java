package com.example.interceptortest.domain.controller;

import com.example.interceptortest.domain.config.MyAnnotation;
import com.example.interceptortest.domain.role.Role;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AdminController {

    @MyAnnotation(role = Role.ADMIN)
    @GetMapping("/admin")
    public String admin() {
        return "관리자 페이지입니다.";
    }

}
