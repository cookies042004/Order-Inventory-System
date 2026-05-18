package com.company.order_inventory_system.product.exception;

// Custom exception for missing product
public class ProductNotFoundException extends RuntimeException {

    // Constructor with custom message
    public ProductNotFoundException(String message) {
        super(message);
    }
}