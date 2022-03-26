package com.example.chat.controller;

import com.example.chat.service.UserService;
import com.example.chat.webSocket.model.MessageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
public class MessageController {

    private SimpMessagingTemplate simpMessagingTemplate;
    private UserService userService;

    @Autowired
    public MessageController(SimpMessagingTemplate simpMessagingTemplate, UserService userService) {
        this.simpMessagingTemplate = simpMessagingTemplate;
        this.userService = userService;
    }

    @MessageMapping("/chat/{from}/{to}")
    public void sendMessage(@DestinationVariable String from, @DestinationVariable String to, MessageModel message) {

        if (userService.findUser(to)) {
            System.out.println("handling send message: " + message + "to: " + to);
            message.setStatus("OK");
            simpMessagingTemplate.convertAndSend("/topic/messages/" + to, message);
            simpMessagingTemplate.convertAndSend("/topic/messages/" + from, message);
        }
    }

//    @MessageMapping("/request_friend/{to}")
//    public void sendFriendRequest(@DestinationVariable String to) {
//        System.out.println("add friend: " + to);
//        simpMessagingTemplate.c
//    }
}
