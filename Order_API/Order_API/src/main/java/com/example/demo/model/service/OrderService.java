package com.example.demo.model.service;

import com.example.demo.model.dao.entity.ProductEntity;

public abstract class OrderService {
	public abstract ProductEntity createProduct(ProductEntity prod);
	public abstract ProductEntity findProductById(ProductEntity prod);
	public abstract void updateQuantity(ProductEntity prod);
}
