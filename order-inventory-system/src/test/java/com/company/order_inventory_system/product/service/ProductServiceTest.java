package com.company.order_inventory_system.product.service;

import com.company.order_inventory_system.product.dto.ProductRequest;
import com.company.order_inventory_system.product.dto.ProductResponse;

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

    @InjectMocks // Injects mock repository into service
    private ProductServiceImpl productService;

    private Product product;

    private ProductRequest productRequest;

    // Runs before each test method
    @BeforeEach
    void setUp() {

        MockitoAnnotations.openMocks(this);

        // Sample entity object
        product = new Product(
                1,
                "Nike Shoes",
                4999.0,
                "BLACK",
                "NIKE",
                "M",
                5
        );

        // Sample request DTO object
        productRequest = new ProductRequest(
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
        when(productRepository.save(any(Product.class)))
                .thenReturn(product);

        ProductResponse savedProduct =
                productService.createProduct(productRequest);

        // Validate response DTO
        assertNotNull(savedProduct);

        assertEquals(
                "Nike Shoes",
                savedProduct.getProductName()
        );

        verify(productRepository, times(1))
                .save(any(Product.class));
    }

    // Tests fetching all products
    @Test
    void testGetAllProducts() {

        List<Product> productList =
                Arrays.asList(product);

        // Mock repository response
        when(productRepository.findAll())
                .thenReturn(productList);

        List<ProductResponse> products =
                productService.getAllProducts();

        // Validate response size
        assertEquals(1, products.size());

        verify(productRepository, times(1))
                .findAll();
    }

    // Tests fetching product by ID
    @Test
    void testGetProductById() {

        // Mock repository response
        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        ProductResponse foundProduct =
                productService.getProductById(1);

        // Validate returned product
        assertNotNull(foundProduct);

        assertEquals(
                "NIKE",
                foundProduct.getBrand()
        );

        verify(productRepository, times(1))
                .findById(1);
    }

    // Tests updating existing product
    @Test
    void testUpdateProduct() {

        Product updatedEntity = new Product(
                1,
                "Updated Shoes",
                5999.0,
                "BLUE",
                "NIKE",
                "L",
                4
        );

        ProductRequest updatedRequest =
                new ProductRequest(
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
                .thenReturn(updatedEntity);

        ProductResponse result =
                productService.updateProduct(
                        1,
                        updatedRequest
                );

        // Validate updated values
        assertEquals(
                "Updated Shoes",
                result.getProductName()
        );

        assertEquals(
                "BLUE",
                result.getColour()
        );

        verify(productRepository, times(1))
                .save(any(Product.class));
    }

    // Tests deleting product
    @Test
    void testDeleteProduct() {

        when(productRepository.findById(1))
                .thenReturn(Optional.of(product));

        doNothing()
                .when(productRepository)
                .delete(product);

        productService.deleteProduct(1);

        verify(productRepository, times(1))
                .delete(product);
    }

    // Tests fetching products by brand
    @Test
    void testGetProductsByBrand() {

        // Mock repository method
        when(productRepository.findByBrand("NIKE"))
                .thenReturn(Arrays.asList(product));

        List<ProductResponse> products =
                productService.getProductsByBrand("NIKE");

        // Validate returned list
        assertFalse(products.isEmpty());

        verify(productRepository, times(1))
                .findByBrand("NIKE");
    }
}