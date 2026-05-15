package com.company.order_inventory_system.product.repository;

import com.company.order_inventory_system.product.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // Repository layer testing
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void testCreateProduct() {

        Product product = new Product();

        product.setProductName("Test Product");
        product.setUnitPrice(5000.0);
        product.setColour("BLACK");
        product.setBrand("TEST");
        product.setSize("M");
        product.setRating(5);

        Product savedProduct = productRepository.save(product);

        assertNotNull(savedProduct.getProductId());
    }

    @Test
    void testGetProductById() {

        Optional<Product> product = productRepository.findById(1);

        assertTrue(product.isPresent());
    }

    @Test
    void testFindByBrand() {

        List<Product> products = productRepository.findByBrand("H & M");

        assertFalse(products.isEmpty());
    }

    @Test
    void testUpdateProduct() {

        Product product = productRepository.findById(1).orElse(null);

        assertNotNull(product);

        product.setColour("BLUE");

        Product updatedProduct = productRepository.save(product);

        assertEquals("BLUE", updatedProduct.getColour());
    }

    @Test
    void testDeleteProduct() {

        Product product = new Product();

        product.setProductName("Delete Test");
        product.setUnitPrice(1000.0);
        product.setColour("RED");
        product.setBrand("TEST");
        product.setSize("L");
        product.setRating(4);

        Product savedProduct = productRepository.save(product);

        Integer productId = savedProduct.getProductId();

        productRepository.deleteById(productId);

        Optional<Product> deletedProduct = productRepository.findById(productId);

        assertFalse(deletedProduct.isPresent());
    }
}