package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.mock;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.example.demo.controller.AuthController;
import com.example.demo.controller.dto.JwtRequest;
import com.example.demo.controller.dto.JwtResponse;
import com.example.demo.model.service.AuthService;

public class AuthControllerTest {

    @InjectMocks
    private AuthController authController;

    @Mock
    private AuthService authService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }
	
	@Test
	public void testLogin1() {
	    JwtRequest request = new JwtRequest();
	    request.setPassword("password123");

	    authController.login(request);

	}

	@Test
	public void testLogin2() {
	    JwtRequest request = new JwtRequest();
	    request.setEmail("user@example.com");

	    authController.login(request);

	  	}

	@Test
	public void testLogin3() {
	    JwtRequest request = new JwtRequest();
	    request.setEmail("");
	    request.setPassword("");


	  authController.login(request);

	}

}
