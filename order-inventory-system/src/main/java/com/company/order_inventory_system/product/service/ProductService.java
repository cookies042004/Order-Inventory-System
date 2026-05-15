package com.company.order_inventory_system.product.service;

import com.company.order_inventory_system.product.entity.Product;
import com.company.order_inventory_system.product.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Contains business logic
public class ProductService {

    private final ProductRepository productRepository;

    // Constructor Injection
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Save new product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Get product by ID
    public Optional<Product> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    // Update existing product
    public Product updateProduct(Integer productId, Product updatedProduct) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
        existingProduct.setColour(updatedProduct.getColour());
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setSize(updatedProduct.getSize());
        existingProduct.setRating(updatedProduct.getRating());

        return productRepository.save(existingProduct);
    }

    // Delete product
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    // Find products by brand
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    // Find products by colour
    public List<Product> getProductsByColour(String colour) {
        return productRepository.findByColour(colour);
    }

    // Find products by size
    public List<Product> getProductsBySize(String size) {
        return productRepository.findBySize(size);
    }
}