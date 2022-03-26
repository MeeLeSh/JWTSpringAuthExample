package com.example.chat.controller;

import com.example.chat.model.dto.RegistrationUserRequest;
import com.example.chat.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@CrossOrigin
@RequestMapping("/auth/")
public class AuthController {

    private UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;


    }
    @GetMapping("login")
    public String loginPage() { return "login"; }

    @GetMapping("registration")
    public String registrationPage() { return "registration"; }

    @PostMapping("registration")
    public ResponseEntity registration(RegistrationUserRequest registrationUserRequest) {
        return userService.registrationUser(registrationUserRequest);
    }

}
