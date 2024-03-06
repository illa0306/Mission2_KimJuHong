package com.example.shopping.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/** 로그인 페이지 */

@Slf4j
@Controller
@RequestMapping("users")
@RequiredArgsConstructor
public class LoginController {
    private final PasswordEncoder passwordEncoder;
    private final UserDetailsManager userDetailsManager;


    @GetMapping("/login")
    public String loginForm() {
        return "done";
    }

    @GetMapping("/profile")
    public String profile(
            Authentication authentication,
            Model model
    ) {
        log.info(authentication.getName());
        log.info(((User) authentication.getPrincipal()).getUsername());
        return "done";
    }

    @PostMapping("/register")
    public String signUpRequest(
            @RequestParam("username")
            String username,
            @RequestParam("password")
            String password,
            @RequestParam("password-check")
            String passwordCheck
    ) {
        if (password.equals(passwordCheck)) {
            userDetailsManager.createUser(User.withUsername(username)
                    .password(passwordEncoder.encode(password))
                    .build());
        } else {
            return "done";
        }
        return "redirect:/users/login";
    }
}
