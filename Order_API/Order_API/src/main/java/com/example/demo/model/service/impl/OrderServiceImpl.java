package com.example.demo.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.ProductRepository;
import com.example.demo.model.dao.entity.ProductEntity;
import com.example.demo.model.service.OrderService;

@Service
public class OrderServiceImpl extends OrderService {
	@Autowired
	private ProductRepository prodRepo;
	
	@Override
	public ProductEntity createProduct(ProductEntity prod) {
		return prodRepo.save(prod);
	}
	
	@Override
	public ProductEntity findProductById(ProductEntity prod) {
		return prodRepo.getById(prod.getProductId());
	}
	
	@Override
	public void updateQuantity(ProductEntity prod) {
		prodRepo.saveAndFlush(prod);
	}
}
