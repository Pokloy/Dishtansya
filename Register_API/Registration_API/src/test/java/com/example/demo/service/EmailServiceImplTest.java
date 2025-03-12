package com.example.demo.service;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import com.example.demo.model.service.impl.EmailServiceImpl;

public class EmailServiceImplTest {

    @InjectMocks
    private EmailServiceImpl emailService;

    @Mock
    private JavaMailSender mailSender;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testSendRegistrationEmail() {
        // Arrange
        String recipientEmail = "test@example.com";

        // Act
        emailService.sendRegistrationEmail(recipientEmail);

        // Assert
        verify(mailSender, times(1)).send(any(SimpleMailMessage.class));
    }
}
