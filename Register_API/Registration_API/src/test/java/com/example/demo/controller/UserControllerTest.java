package com.example.demo.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.model.dao.entity.UserEntity;
import com.example.demo.model.service.UserService;

public class UserControllerTest {

    AutoCloseable closeable;

    @InjectMocks
    UserController userController;

    @Mock
    UserService userService;

    @BeforeEach
    public void openMocks() {
        closeable = MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testUserController1() {
        // Given
        UserEntity newUser = new UserEntity();
        newUser.setEmail("testuser@gmail.com");
        newUser.setPassword("securePass123");
        when(userService.findUserByEmail(any())).thenReturn(null);
      //  doNothing().when(userService).createUser(newUser);

        // When
        ResponseEntity<Map<String, String>> response = userController.createUser(newUser);

        // Then
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("User successfully registered", response.getBody().get("message"));
    }

    @Test
    public void testUserController2() {
        // Given
        UserEntity invalidUser = new UserEntity();
        invalidUser.setEmail("");
        invalidUser.setPassword("password123");
        // When
        ResponseEntity<Map<String, String>> response = userController.createUser(invalidUser);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Please fill out completely", response.getBody().get("message"));
    }
    
    

    @Test
    public void testUserController3() {
        // Given
        UserEntity invalidUser = new UserEntity();
        invalidUser.setEmail("invalid-email");
        invalidUser.setPassword("password123");
        // When
        ResponseEntity<Map<String, String>> response = userController.createUser(invalidUser);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Invalid Email Format", response.getBody().get("message"));
    }

    @Test
    public void testUserControlle4() {
        // Given
        UserEntity existingUser = new UserEntity();
        existingUser.setEmail("existing@gmail.com");
        existingUser.setPassword("password123");
        when(userService.findUserByEmail(any())).thenReturn(existingUser);

        // When
        ResponseEntity<Map<String, String>> response = userController.createUser(existingUser);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Email already taken", response.getBody().get("message"));
    }
    
    @Test
    public void testUserController5() {
        // Given
        UserEntity invalidUser = new UserEntity();
        invalidUser.setEmail("test51@gmail.com");
        invalidUser.setPassword("");
        // When
        ResponseEntity<Map<String, String>> response = userController.createUser(invalidUser);

        // Then
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Please fill out completely", response.getBody().get("message"));
    }
}
