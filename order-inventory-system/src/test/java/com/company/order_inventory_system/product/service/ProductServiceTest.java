package com.company.order_inventory_system.product.service;

import com.company.order_inventory_system.product.entity.Product;
import com.company.order_inventory_system.product.repository.ProductRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ProductServiceTest {

    @Mock // Creates mock repository object
    private ProductRepository productRepository;

    @InjectMocks // Injects mock repository into service implementation
    private ProductServiceImpl productService;

    private Product product;

    // Runs before each test method
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        // Sample product object used for testing
        product = new Product(
                1,
                "Nike Shoes",
                4999.0,
                "BLACK",
                "NIKE",
                "M",
                5
        );
    }

    // Tests product creation service
    @Test
    void testCreateProduct() {

        // Mock repository save response
        when(productRepository.save(product)).thenReturn(product);

        Product savedProduct = productService.createProduct(product);

        // Validate saved product
        assertNotNull(savedProduct);
        assertEquals("Nike Shoes", savedProduct.getProductName());

        // Verify save method called once
        verify(productRepository, times(1)).save(product);
    }

    // Tests fetching all products
    @Test
    void testGetAllProducts() {

        List<Product> productList = Arrays.asList(product);

        // Mock repository findAll response
        when(productRepository.findAll()).thenReturn(productList);

        List<Product> products = productService.getAllProducts();

        // Validate product list size
        assertEquals(1, products.size());

        verify(productRepository, times(1)).findAll();
    }

    // Tests fetching product by ID
    @Test
    void testGetProductById() {

        // Mock repository findById response
        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productService.getProductById(1);

        // Validate returned product
        assertTrue(foundProduct.isPresent());
        assertEquals("NIKE", foundProduct.get().getBrand());

        verify(productRepository, times(1)).findById(1);
    }

    // Tests updating existing product
    @Test
    void testUpdateProduct() {

        Product updatedProduct = new Product(
                1,
                "Updated Shoes",
                5999.0,
                "BLUE",
                "NIKE",
                "L",
                4
        );

        // Mock existing product fetch
        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        // Mock updated save response
        when(productRepository.save(any(Product.class)))
                .thenReturn(updatedProduct);

        Product result = productService.updateProduct(1, updatedProduct);

        // Validate updated values
        assertEquals("Updated Shoes", result.getProductName());
        assertEquals("BLUE", result.getColour());

        verify(productRepository, times(1)).save(any(Product.class));
    }

    // Tests deleting product
    @Test
    void testDeleteProduct() {

        // Mock delete operation
        doNothing().when(productRepository).deleteById(1);

        productService.deleteProduct(1);

        // Verify delete method execution
        verify(productRepository, times(1)).deleteById(1);
    }

    // Tests fetching products by brand
    @Test
    void testGetProductsByBrand() {

        // Mock custom repository method
        when(productRepository.findByBrand("NIKE"))
                .thenReturn(Arrays.asList(product));

        List<Product> products = productService.getProductsByBrand("NIKE");

        // Validate returned list
        assertFalse(products.isEmpty());

        verify(productRepository, times(1)).findByBrand("NIKE");
    }
}