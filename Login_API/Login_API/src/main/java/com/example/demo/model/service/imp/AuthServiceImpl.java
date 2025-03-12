package com.example.demo.model.service.imp;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.controller.dto.JwtRequest;
import com.example.demo.model.dao.UserRepository;
import com.example.demo.model.dao.entity.UserEntity;
import com.example.demo.model.service.AuthService;
import com.example.demo.utility.JwtUtil;

/** 
 * Service for login Authentication
 * @since 12/02/2025
 * @author alier
 * */
@Service
public class AuthServiceImpl extends AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;
    
    private static final int MAX_ATTEMPTS = 5;
    private static final int LOCKOUT_DURATION_MINUTES = 5;
    
    /** 
	 * method for authenticating user
	 * @since 12/02/2025
	 * @author alier
	 * */
    @Transactional
    public ResponseEntity<?> authenticate(JwtRequest request) {
        UserEntity user = userRepository.findUserByEmail(request.getEmail());
        Map<String, String> response = new HashMap<>();
        
        // Check if the user result of find email is null
        if (user == null) {
            response.put("message", "Invalid credentials");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        // Check if the account is locked
        if (user.isAccountLocked()) {
            response.put("message", "Account is locked. Try again later.");
            return ResponseEntity.status(HttpStatus.LOCKED).body(response);
        }

        // Verify password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            user.setFailedAttempts(user.getFailedAttempts() + 1);
            // Verify failed attemts
            if (user.getFailedAttempts() >= MAX_ATTEMPTS) {
                user.setLockoutTime(LocalDateTime.now().plusMinutes(LOCKOUT_DURATION_MINUTES));
                response.put("message", "Too many failed attempts. Account locked for 5 minutes.");
                userRepository.save(user);
                return ResponseEntity.status(HttpStatus.LOCKED).body(response);
            }

            response.put("message", "Invalid credentials");
            userRepository.save(user);
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(response);
        }

        
        user.setFailedAttempts(0);
        user.setLockoutTime(null);
        userRepository.save(user);

        String accessToken = jwtUtil.generateToken(user.getEmail());
        response.put("access_token", accessToken);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }
}
