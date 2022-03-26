package com.example.chat.controller;

import com.example.chat.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Set;

@Controller
@CrossOrigin
public class ChatController {

    private UserService userService;

    public ChatController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping("/chat")
    @PreAuthorize("hasAnyRole('USER')")
    public String chatPage(Model model, Authentication authentication) {
        Set<String> friendNames = userService.getAllFriends(authentication.getName());
        model.addAttribute("friends", friendNames);
        return "chat";
    }
}
