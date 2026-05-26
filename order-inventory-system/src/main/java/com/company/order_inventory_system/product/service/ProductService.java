package com.company.order_inventory_system.product.service;

import com.company.order_inventory_system.product.dto.ProductRequest;
import com.company.order_inventory_system.product.dto.ProductResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

// Defines product business operations
public interface ProductService {

    //method
    // Creates new product
    ProductResponse createProduct(ProductRequest productRequest);

    // Fetches all products
    List<ProductResponse> getAllProducts();

    // Fetches all products (paginated)
    Page<ProductResponse> getAllProducts(Pageable pageable);

    // Fetches product by ID
    ProductResponse getProductById(Integer productId);

    // Updates existing product
    ProductResponse updateProduct(
            Integer productId,
            ProductRequest productRequest
    );

    // Deletes product by ID
    ProductResponse deleteProduct(Integer productId);

}