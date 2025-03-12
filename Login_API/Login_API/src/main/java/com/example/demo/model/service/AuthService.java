package com.example.demo.model.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.controller.dto.JwtRequest;
import com.example.demo.controller.dto.JwtResponse;

/** 
 * Service for login Authentication
 * @since 12/02/2025
 * @author alier
 * */
public abstract class AuthService {
	/** 
	 * method for authenticating user
	 * @since 12/02/2025
	 * @author alier
	 * */
	public abstract ResponseEntity<?> authenticate(JwtRequest request);
}
