package com.example.demo.model.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.dao.entity.ProductEntity;

public interface ProductRepository extends JpaRepository<ProductEntity, Long> {
	
}
