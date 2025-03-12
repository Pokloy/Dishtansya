package com.example.demo.model.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dao.ProductRepository;
import com.example.demo.model.dao.entity.ProductEntity;
import com.example.demo.model.service.OrderService;

/** 
 * service Class for order service
 * @since 12/02/2025
 * @author alier
 * */
@Service
public class OrderServiceImpl extends OrderService {
	@Autowired
	private ProductRepository prodRepo;
	
	/** 
	 * method for creating new product
	 * @since 12/02/2025
	 * @author alier
	 * */
	@Override
	public ProductEntity createProduct(ProductEntity prod) {
		return prodRepo.save(prod);
	}
	
	/** 
	 * method for finding product by ID
	 * @since 12/02/2025
	 * @author alier
	 * */
	@Override
	public ProductEntity findProductById(ProductEntity prod) {
		return prodRepo.getById(prod.getProductId());
	}
	
	/** 
	 * method for finding updating Quantity of product
	 * @since 12/02/2025
	 * @author alier
	 * */
	@Override
	public void updateQuantity(ProductEntity prod) {
		prodRepo.saveAndFlush(prod);
	}
}
