package com.example.chat.controller;

import com.example.chat.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserInfoController {

    private UserService userService;

    public UserInfoController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/my_name")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> getMyName(Authentication authentication) {
        return new ResponseEntity<>(authentication.getName(), HttpStatus.OK);
    }

    @GetMapping("/exist/{username}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<String> findUser(@PathVariable String username) {
        if (userService.findUser(username)) { return new ResponseEntity<>("TRUE", HttpStatus.OK); }
        return new ResponseEntity<>("FALSE", HttpStatus.OK);
    }
}
