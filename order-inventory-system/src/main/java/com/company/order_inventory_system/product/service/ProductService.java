package com.company.order_inventory_system.product.service;

import com.company.order_inventory_system.product.entity.Product;

import java.util.List;
import java.util.Optional;

// Defines product service operations
public interface ProductService {

    // Save new product
    Product createProduct(Product product);

    List<Product> getAllProducts();

    // Fetch product using ID
    Optional<Product> getProductById(Integer productId);

    Product updateProduct(Integer productId, Product updatedProduct);

    // Delete product by ID
    void deleteProduct(Integer productId);

    List<Product> getProductsByBrand(String brand);

    List<Product> getProductsByColour(String colour);

    List<Product> getProductsBySize(String size);
}