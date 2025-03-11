package com.example.demo.model.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.model.service.EmailService;

@Service
public class EmailServiceImpl extends EmailService {
    @Async
    public void sendRegistrationEmail(String to) {
        System.out.println("Sending registration email to: " + to);
    }
}
