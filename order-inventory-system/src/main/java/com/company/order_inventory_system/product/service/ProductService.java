package com.company.order_inventory_system.product.service;

import com.company.order_inventory_system.product.dto.ProductRequest;
import com.company.order_inventory_system.product.dto.ProductResponse;

import java.util.List;

// Defines product business operations
public interface ProductService {

    // Creates new product
    ProductResponse createProduct(ProductRequest productRequest);

    // Fetches all products
    List<ProductResponse> getAllProducts();

    // Fetches product by ID
    ProductResponse getProductById(Integer productId);

    // Updates existing product
    ProductResponse updateProduct(
            Integer productId,
            ProductRequest productRequest
    );

    // Deletes product by ID
    void deleteProduct(Integer productId);

    // Fetches products by brand
    List<ProductResponse> getProductsByBrand(String brand);

    // Fetches products by colour
    List<ProductResponse> getProductsByColour(String colour);

    // Fetches products by size
    List<ProductResponse> getProductsBySize(String size);
}