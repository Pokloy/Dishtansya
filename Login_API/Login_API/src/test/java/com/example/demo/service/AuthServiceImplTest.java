package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.demo.controller.dto.JwtRequest;
import com.example.demo.model.dao.UserRepository;
import com.example.demo.model.dao.entity.UserEntity;
import com.example.demo.model.service.imp.AuthServiceImpl;
import com.example.demo.utility.JwtUtil;
import org.springframework.http.HttpStatus;

public class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authServiceImpl;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtUtil jwtUtil;

    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testAuthenticate1() {
        // Arrange
        JwtRequest mockJwtRequest = new JwtRequest();
        mockJwtRequest.setEmail("testEmail@gmail.com");
        mockJwtRequest.setPassword("TestPass");

        UserEntity mockUser = new UserEntity();
        mockUser.setEmail("testEmail@gmail.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setFailedAttempts(0);
        mockUser.setLockoutTime(null);

        when(userRepository.findUserByEmail("testEmail@gmail.com")).thenReturn(mockUser);
        when(passwordEncoder.matches("TestPass", "encodedPassword")).thenReturn(true);
        when(jwtUtil.generateToken("testEmail@gmail.com")).thenReturn("mockedToken");

        // Act
        ResponseEntity<?> response = authServiceImpl.authenticate(mockJwtRequest);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("mockedToken", ((Map<?, ?>) response.getBody()).get("access_token"));

        // Verify failed attempts reset to 0
        assertEquals(0, mockUser.getFailedAttempts());
    }

    @Test
    public void testAuthenticate2() {
        // Arrange
        JwtRequest mockJwtRequest = new JwtRequest();
        mockJwtRequest.setEmail("testEmail@gmail.com");
        mockJwtRequest.setPassword("WrongPass");

        UserEntity mockUser = new UserEntity();
        mockUser.setEmail("testEmail@gmail.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setFailedAttempts(2);
        mockUser.setLockoutTime(null);

        when(userRepository.findUserByEmail("testEmail@gmail.com")).thenReturn(mockUser);
        when(passwordEncoder.matches("WrongPass", "encodedPassword")).thenReturn(false);

        // Act
        ResponseEntity<?> response = authServiceImpl.authenticate(mockJwtRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("Invalid credentials", ((Map<?, ?>) response.getBody()).get("message"));

        // Verify failed attempts incremented
        assertEquals(3, mockUser.getFailedAttempts());
    }

    @Test
    public void testAuthenticate3() {
        // Arrange
        JwtRequest mockJwtRequest = new JwtRequest();
        mockJwtRequest.setEmail("testEmail@gmail.com");
        mockJwtRequest.setPassword("TestPass");

        UserEntity mockUser = new UserEntity();
        mockUser.setEmail("testEmail@gmail.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setFailedAttempts(5);
        mockUser.setLockoutTime(LocalDateTime.now().plusMinutes(3)); // Still locked

        when(userRepository.findUserByEmail("testEmail@gmail.com")).thenReturn(mockUser);

        // Act
        ResponseEntity<?> response = authServiceImpl.authenticate(mockJwtRequest);

        // Assert
        assertEquals(HttpStatus.LOCKED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("Account is locked. Try again later.", ((Map<?, ?>) response.getBody()).get("message"));
    }

    @Test
    public void testAuthenticate4() {
        // Arrange
        JwtRequest mockJwtRequest = new JwtRequest();
        mockJwtRequest.setEmail("unknown@gmail.com");
        mockJwtRequest.setPassword("TestPass");

        when(userRepository.findUserByEmail("unknown@gmail.com")).thenReturn(null);

        // Act
        ResponseEntity<?> response = authServiceImpl.authenticate(mockJwtRequest);

        // Assert
        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("Invalid credentials", ((Map<?, ?>) response.getBody()).get("message"));
    }
    
    @Test
    public void testAuthenticate5() {
        // Arrange
        JwtRequest mockJwtRequest = new JwtRequest();
        mockJwtRequest.setEmail("testEmail@gmail.com");
        mockJwtRequest.setPassword("WrongPass");

        UserEntity mockUser = new UserEntity();
        mockUser.setEmail("testEmail@gmail.com");
        mockUser.setPassword("encodedPassword");
        mockUser.setFailedAttempts(5);
        mockUser.setLockoutTime(null);

        when(userRepository.findUserByEmail("testEmail@gmail.com")).thenReturn(mockUser);
        when(passwordEncoder.matches("WrongPass", "encodedPassword")).thenReturn(false);

        // Act
        ResponseEntity<?> response = authServiceImpl.authenticate(mockJwtRequest);

        // Assert
        assertEquals(HttpStatus.LOCKED, response.getStatusCode());
        assertTrue(response.getBody() instanceof Map);
        assertEquals("Too many failed attempts. Account locked for 5 minutes.", ((Map<?, ?>) response.getBody()).get("message"));

        // Verify failed attempts incremented
        assertEquals(6, mockUser.getFailedAttempts());
    }
}
