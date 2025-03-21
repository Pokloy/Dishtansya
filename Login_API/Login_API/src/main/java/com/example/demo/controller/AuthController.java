package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.controller.dto.JwtRequest;
import com.example.demo.controller.dto.JwtResponse;
import com.example.demo.model.service.AuthService;

/** 
 * Controller for login authentication
 * @since 12/02/2025
 * @author alier
 * */
@RestController
public class AuthController {

    @Autowired
    private AuthService authService;
    
    /** 
     * method for login authentication
     * @since 12/02/2025
     * @author alier
     * */
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody JwtRequest request) {
        return authService.authenticate(request);
    }
}
