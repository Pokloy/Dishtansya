package com.example.demo.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.UserRepository;
import com.example.demo.model.dao.entity.UserEntity;
import com.example.demo.model.service.*;

@Service
public class UserServiceImpl extends UserService {
	@Autowired
	private UserRepository userRepo;
	
    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepo.save(user);
    }
    
    @Override
    public UserEntity findUserByEmail(UserEntity user) {
    	return userRepo.findUserByEmail(user.getEmail());
    }
}
