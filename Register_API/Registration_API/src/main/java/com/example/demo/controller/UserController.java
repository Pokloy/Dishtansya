package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dao.entity.UserEntity;
import com.example.demo.model.service.UserService;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    @PostMapping("/register")
    public ResponseEntity<Map<String, String>> createUser(@RequestBody UserEntity user) {
    	Map <String, String> response = new HashMap<>();
    	
    	UserEntity checkAccount = userService.findUserByEmail(user);
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(emailRegex);
        Matcher matcher = pattern.matcher(user.getEmail());
        
  	  if(user.getEmail() == "" || user.getPassword() == "") {
  		  response.put("message", "Please fill out completely");
		  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
	  }
  	  
      if (!matcher.matches()) {
    	  response.put("message", "Invalid Email Format");
    	  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
      }
      
      if(checkAccount != null) {
    	  response.put("message", "Email already taken");
    	  return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
      }
    	
      userService.createUser(user);
      response.put("message", "User successfully registered");
      return ResponseEntity.status(HttpStatus.CREATED).body(response);

    }
    
    
    @GetMapping("/test-user-service")
    public String test() {
    	return "Test Test ";
    }
}
