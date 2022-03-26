package com.example.chat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ConfigClass {

    @Bean
    public PasswordEncoder passwordSecurityEncoder() {
        return new BCryptPasswordEncoder(12);
    }
}