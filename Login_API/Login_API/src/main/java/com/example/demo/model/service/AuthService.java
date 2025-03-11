package com.example.demo.model.service;

import org.springframework.http.ResponseEntity;

import com.example.demo.controller.dto.JwtRequest;
import com.example.demo.controller.dto.JwtResponse;

public abstract class AuthService {
	public abstract ResponseEntity<?> authenticate(JwtRequest request);
}
