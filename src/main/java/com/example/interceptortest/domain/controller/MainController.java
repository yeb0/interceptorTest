package com.example.interceptortest.domain.controller;

import com.example.interceptortest.domain.config.MyAnnotation;
import com.example.interceptortest.domain.repository.UserRepository;
import com.example.interceptortest.domain.role.Role;
import com.example.interceptortest.domain.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
public class MainController {
    private final UserRepository userRepository;

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/index/login")
    public String login(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") != null) {
            User user = (User) httpSession.getAttribute("user");
            model.addAttribute("user", user);
            return "redirect:my-info";
        }
        return "/index/login";
    }
    @PostMapping("/index/login")
    public String loginSubmit(@RequestParam("email") String email,
                              @RequestParam("password") String password,
                              HttpSession httpSession) {
        if (!userRepository.isUser(email, password)) {
            return "redirect:login";
        }
        httpSession.setAttribute("user", userRepository.findByEmail(email));
        return "redirect:/index";
    }

    @MyAnnotation(role = Role.USER)
    @GetMapping("/index/logout")
    public String logout (HttpSession httpSession) {
        httpSession.invalidate();
        return "redirect:/index";
    }
    @GetMapping("index/my-info")
    public String myInfo(Model model, HttpSession httpSession) {
        if (httpSession.getAttribute("user") != null) {
            User user = (User) httpSession.getAttribute("user");
            model.addAttribute("user", user);
        } else {
            model.addAttribute("user", new User());
        }
        return "/index/my-info";
    }
}
