package com.example.demo.model.dao;

import org.springframework.dao.DataAccessException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.example.demo.model.dao.entity.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
	final String FIND_USER_BY_EMAIL = " SELECT e FROM UserEntity e WHERE e.email = :email ";
	
	
	@Query(value=FIND_USER_BY_EMAIL)
	public UserEntity findUserByEmail(String email) throws DataAccessException;
}
