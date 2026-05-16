package com.company.order_inventory_system.product.service;

import com.company.order_inventory_system.product.entity.Product;
import com.company.order_inventory_system.product.repository.ProductRepository;
import com.company.order_inventory_system.product.exception.ProductNotFoundException;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service // Contains business logic implementation
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Constructor Injection
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Save new product
    @Override
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    // Fetch all products
    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Fetch product by ID
    @Override
    public Optional<Product> getProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    // Update existing product
    @Override
    public Product updateProduct(Integer productId, Product updatedProduct) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product not found with ID : " + productId));

        existingProduct.setProductName(updatedProduct.getProductName());
        existingProduct.setUnitPrice(updatedProduct.getUnitPrice());
        existingProduct.setColour(updatedProduct.getColour());
        existingProduct.setBrand(updatedProduct.getBrand());
        existingProduct.setSize(updatedProduct.getSize());
        existingProduct.setRating(updatedProduct.getRating());

        return productRepository.save(existingProduct);
    }

    // Delete product
    @Override
    public void deleteProduct(Integer productId) {
        productRepository.deleteById(productId);
    }

    // Fetch products by brand
    @Override
    public List<Product> getProductsByBrand(String brand) {
        return productRepository.findByBrand(brand);
    }

    // Fetch products by colour
    @Override
    public List<Product> getProductsByColour(String colour) {
        return productRepository.findByColour(colour);
    }

    // Fetch products by size
    @Override
    public List<Product> getProductsBySize(String size) {
        return productRepository.findBySize(size);
    }
}