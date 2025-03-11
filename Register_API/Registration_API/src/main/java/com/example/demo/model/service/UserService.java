package com.example.demo.model.service;

import com.example.demo.model.dao.entity.UserEntity;

public abstract class UserService {
	public abstract UserEntity createUser(UserEntity user);
	public abstract UserEntity findUserByEmail(UserEntity user);
}
