package com.example.demo.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.dao.UserRepository;
import com.example.demo.model.dao.entity.UserEntity;
import com.example.demo.model.service.impl.UserServiceImpl;
import com.example.demo.model.service.EmailService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class UserServiceImplTest {

    @InjectMocks
    private UserServiceImpl userService;

    @Mock
    private UserRepository userRepo;

    @Mock
    private EmailService emailService;

    @Mock
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateUser1() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setEmail("test@example.com");
        user.setPassword("plainPassword");

        when(passwordEncoder.encode("plainPassword")).thenReturn("encodedPassword");
        when(userRepo.save(any(UserEntity.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        UserEntity savedUser = userService.createUser(user);

        // Assert
        assertNotNull(savedUser);
    }

    @Test
    public void testFindUserByEmail2() {
        // Arrange
        UserEntity mockUser = new UserEntity();
        mockUser.setEmail("test@example.com");

        when(userRepo.findUserByEmail("test@example.com")).thenReturn(mockUser);

        // Act
        UserEntity foundUser = userService.findUserByEmail(mockUser);

        // Assert
        assertNotNull(foundUser);
        assertEquals("test@example.com", foundUser.getEmail());
        verify(userRepo, times(1)).findUserByEmail("test@example.com");
    }

    @Test
    public void testFindUserByEmail3() {
        // Arrange
        UserEntity user = new UserEntity();
        user.setEmail("unknown@example.com");

        when(userRepo.findUserByEmail("unknown@example.com")).thenReturn(null);

        // Act
        UserEntity foundUser = userService.findUserByEmail(user);

        // Assert
        assertNull(foundUser);
        verify(userRepo, times(1)).findUserByEmail("unknown@example.com");
    }
}
