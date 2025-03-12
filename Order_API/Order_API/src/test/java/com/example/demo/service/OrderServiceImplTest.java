package com.example.demo.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.demo.model.dao.ProductRepository;
import com.example.demo.model.dao.entity.ProductEntity;
import com.example.demo.model.service.impl.OrderServiceImpl;

public class OrderServiceImplTest {

    @InjectMocks
    private OrderServiceImpl orderService;

    @Mock
    private ProductRepository productRepository;

    @BeforeEach 
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateProduct() {
        // Arrange
        ProductEntity product = new ProductEntity();
        when(productRepository.save(product)).thenReturn(product);

        // Act
        ProductEntity createdProduct = orderService.createProduct(product);

        // Assert
        assertNotNull(createdProduct);
        verify(productRepository, times(1)).save(product);
    }

    @Test
    public void testFindProductById() {
        // Arrange
        ProductEntity product = new ProductEntity();
        product.setProductId(1L);
        when(productRepository.getById(1L)).thenReturn(product);

        // Act
        ProductEntity foundProduct = orderService.findProductById(product);

        // Assert
        assertNotNull(foundProduct);
        assertEquals(1L, foundProduct.getProductId());
        verify(productRepository, times(1)).getById(1L);
    }

    @Test
    public void testUpdateQuantity() {
        // Arrange
        ProductEntity product = new ProductEntity();
        when(productRepository.saveAndFlush(product)).thenReturn(product);

        // Act
        orderService.updateQuantity(product);

        // Assert
        verify(productRepository, times(1)).saveAndFlush(product);
    }
}
