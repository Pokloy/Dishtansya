package com.example.demo.model.service;

import com.example.demo.model.dao.entity.ProductEntity;

/** 
 * service Class for order service
 * @since 12/02/2025
 * @author alier
 * */
public abstract class OrderService {
	/** 
	 * method for creating new product
	 * @since 12/02/2025
	 * @author alier
	 * */
	public abstract ProductEntity createProduct(ProductEntity prod);
	
	/** 
	 * method for finding product by ID
	 * @since 12/02/2025
	 * @author alier
	 * */
	public abstract ProductEntity findProductById(ProductEntity prod);
	
	/** 
	 * method for finding updating Quantity of product
	 * @since 12/02/2025
	 * @author alier
	 * */
	public abstract void updateQuantity(ProductEntity prod);
}
