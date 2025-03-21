package com.example.demo.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.example.demo.model.service.EmailService;

/**
 * service for email sending notification
 * @since 12/02/2025
 * @author alier
 * */
@Service
public class EmailServiceImpl extends EmailService {
    @Autowired
    private JavaMailSender mailSender;
	
	/**
	 * method for email sending notification
	 * @since 12/02/2025
	 * @author alier
	 * */
    @Async
    public void sendRegistrationEmail(String to) {
    	 SimpleMailMessage message = new SimpleMailMessage();
         message.setTo(to);
         message.setSubject("Registration Successful");
         message.setText("You are successfully registered.");
         message.setFrom("dishtansya@gmail.com");

         mailSender.send(message);
    }
}
