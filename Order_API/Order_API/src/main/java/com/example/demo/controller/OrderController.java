package com.example.demo.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dao.entity.ProductEntity;
import com.example.demo.model.service.OrderService;

@RestController
public class OrderController {
	@Autowired
	OrderService orderserve;
	
	@PostMapping("/product")
	public ResponseEntity<Map<String, String>> createProduct(@RequestBody ProductEntity prod){
		Map <String, String> response = new HashMap<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
        if (authentication != null && 
        		authentication.isAuthenticated()) {
        	if(prod.getProductName().equals("") || prod.getQuantity() == null) {
    			return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    		}  else {
    			orderserve.createProduct(prod);
    			response.put("message", "Product Registered Successfully.");
    			return ResponseEntity.status(HttpStatus.CREATED).body(response);
    		}
        } 
        
        response.put("message", "Invalid Token");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
	
	@PostMapping("/order")
	public ResponseEntity<Map<String, String>> orderProduct(@RequestBody ProductEntity prod){
		Map <String, String> response = new HashMap<>();
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ProductEntity prodEntity = orderserve.findProductById(prod);
		
		
        if (authentication != null && 
        		authentication.isAuthenticated()) {
        	if(prodEntity == null) {
    	        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
    		}
        	int prodQuantity = prodEntity.getQuantity();
        	prodQuantity -= prod.getQuantity();
        	int quantityResult = prodQuantity;
        	if(0 >= quantityResult) {
        		response.put("message", "Failed to order this product due to unavailability of the stock");
    	        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        	}
        	prod.setQuantity(quantityResult);
        	orderserve.updateQuantity(prod);
	        response.put("message", "You have successfully ordered this product.");
	        return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } 
        
        return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
	}
}