package com.example.chat.webSocket.model;

import lombok.Data;

@Data
public class MessageModel {

    private String message;
    private String from;
    private String to;
    private String status;

    public MessageModel(String message, String from, String to, String status) {
        this.message = message;
        this.from = from;
        this.to = to;
        this.status = status;
    }
}
