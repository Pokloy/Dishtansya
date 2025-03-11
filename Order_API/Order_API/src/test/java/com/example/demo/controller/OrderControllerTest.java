package com.example.demo.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import com.example.demo.model.dao.entity.ProductEntity;
import com.example.demo.model.service.OrderService;

public class OrderControllerTest {

    @InjectMocks
    private OrderController orderController;

    @Mock
    private OrderService orderService;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testOrderProduct1() {
        when(securityContext.getAuthentication()).thenReturn(null);
        
        ResponseEntity<Map<String, String>> response = orderController.orderProduct(new ProductEntity());
        
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testOrderProduct2() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(orderService.findProductById(any(ProductEntity.class))).thenReturn(null);
        
        ResponseEntity<Map<String, String>> response = orderController.orderProduct(new ProductEntity());
        
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testOrderProduct3() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        
        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setQuantity(2);
        
        ProductEntity orderRequest = new ProductEntity();
        orderRequest.setQuantity(3);
        
        when(orderService.findProductById(any(ProductEntity.class))).thenReturn(existingProduct);
        
        ResponseEntity<Map<String, String>> response = orderController.orderProduct(orderRequest);
        
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Failed to order this product due to unavailability of the stock", response.getBody().get("message"));
    }

    @Test
    public void testOrderProduct4() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        
        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setQuantity(5);
        
        ProductEntity orderRequest = new ProductEntity();
        orderRequest.setQuantity(3);
        
        when(orderService.findProductById(any(ProductEntity.class))).thenReturn(existingProduct);
        doNothing().when(orderService).updateQuantity(any(ProductEntity.class));
        
        ResponseEntity<Map<String, String>> response = orderController.orderProduct(orderRequest);
        
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("You have successfully ordered this product.", response.getBody().get("message"));
    }
    
    @Test
    public void testOrderProduct5() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
        
        ProductEntity existingProduct = new ProductEntity();
        existingProduct.setQuantity(2);
        
        ProductEntity orderRequest = new ProductEntity();
        orderRequest.setQuantity(3);
        
        when(orderService.findProductById(any(ProductEntity.class))).thenReturn(existingProduct);
        
        ResponseEntity<Map<String, String>> response = orderController.orderProduct(orderRequest);
        
        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
    
    @Test
    public void testCreateProduct1() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);

        ProductEntity prod = new ProductEntity();
        prod.setProductName("Sample Product");
        prod.setQuantity(10);

        ResponseEntity<Map<String, String>> response = orderController.createProduct(prod);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testCreateProduct2() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        ProductEntity prod = new ProductEntity();
        prod.setProductName("");
        prod.setQuantity(10);

        ResponseEntity<Map<String, String>> response = orderController.createProduct(prod);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testCreateProduct3() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);

        ProductEntity prod = new ProductEntity();
        prod.setProductName("Valid Product");
        prod.setQuantity(null);

        ResponseEntity<Map<String, String>> response = orderController.createProduct(prod);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    public void testCreateProduct4() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
       
        ProductEntity prod = new ProductEntity();
        prod.setProductName("New Product");
        prod.setQuantity(20);

        ResponseEntity<Map<String, String>> response = orderController.createProduct(prod);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals("Product Registered Successfully.", response.getBody().get("message"));
    }
    
    
    @Test
    public void testCreateProduct5() {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(false);
       
        ProductEntity prod = new ProductEntity();
        prod.setProductName("New Product");
        prod.setQuantity(20);

        ResponseEntity<Map<String, String>> response = orderController.createProduct(prod);
    }
    
    @Test
    public void testCreateProduct6() {
        when(securityContext.getAuthentication()).thenReturn(null);
        when(authentication.isAuthenticated()).thenReturn(false);
       
        ProductEntity prod = new ProductEntity();
        prod.setProductName("New Product");
        prod.setQuantity(20);

        ResponseEntity<Map<String, String>> response = orderController.createProduct(prod);
    }
}