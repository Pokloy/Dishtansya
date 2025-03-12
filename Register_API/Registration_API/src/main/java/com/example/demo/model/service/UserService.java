package com.example.demo.model.service;

import com.example.demo.model.dao.entity.UserEntity;

/**
 * service for create and find user
 * @since 12/02/2025
 * @author alier
 * */
public abstract class UserService {
	/**
	 * method for create new user
	 * @since 12/02/2025
	 * @author alier
	 * */
	public abstract UserEntity createUser(UserEntity user);
	
	/**
	 * method for finding user by email
	 * @since 12/02/2025
	 * @author alier
	 * */
	public abstract UserEntity findUserByEmail(UserEntity user);
}
