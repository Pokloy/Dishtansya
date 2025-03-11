package com.example.demo.model.service.imp;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.example.demo.controller.dto.JwtRequest;
import com.example.demo.controller.dto.JwtResponse;
import com.example.demo.model.dao.UserRepository;
import com.example.demo.model.dao.entity.UserEntity;
import com.example.demo.model.service.AuthService;
import com.example.demo.utility.JwtUtil;

@Service
public class AuthServiceImpl extends AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Transactional
    public ResponseEntity<?> authenticate(JwtRequest request) {
        UserEntity user = userRepository.findUserByEmail(request.getEmail());
        Map<String, String> response = new HashMap<>();
        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            
        	response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        String access_token = jwtUtil.generateToken(user.getEmail());
        response.put("access_token", access_token);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}

