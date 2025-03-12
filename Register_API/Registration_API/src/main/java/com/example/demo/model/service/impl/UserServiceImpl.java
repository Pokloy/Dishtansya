package com.example.demo.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.UserRepository;
import com.example.demo.model.dao.entity.UserEntity;
import com.example.demo.model.service.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * service for create and find user
 * @since 12/02/2025
 * @author alier
 * */
@Service
public class UserServiceImpl extends UserService {
	@Autowired
	private UserRepository userRepo;
	
	@Autowired
	private EmailService emailServ;
	
	private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	/**
	 * method for create new user
	 * @since 12/02/2025
	 * @author alier
	 * */
    @Override
    public UserEntity createUser(UserEntity user) {
    	user.setPassword(passwordEncoder.encode(user.getPassword()));
    	emailServ.sendRegistrationEmail(user.getEmail());
        return userRepo.save(user);
    }
    
	/**
	 * method for finding user by email
	 * @since 12/02/2025
	 * @author alier
	 * */
    @Override
    public UserEntity findUserByEmail(UserEntity user) {
    	return userRepo.findUserByEmail(user.getEmail());
    }
}
