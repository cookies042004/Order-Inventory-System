package com.company.order_inventory_system.product.service;

import com.company.order_inventory_system.product.dto.ProductRequest;
import com.company.order_inventory_system.product.dto.ProductResponse;

import com.company.order_inventory_system.product.entity.Product;

import com.company.order_inventory_system.product.exception.ProductNotFoundException;

import com.company.order_inventory_system.product.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service // Contains product business logic
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    // Constructor injection
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    // Creates and saves new product
    @Override
    public ProductResponse createProduct(ProductRequest productRequest) {

        // Converts request DTO to entity
        Product product = mapToEntity(productRequest);

        Product savedProduct = productRepository.save(product);

        // Converts entity to response DTO
        return mapToResponse(savedProduct);
    }

    // Fetches all products
    @Override
    public List<ProductResponse> getAllProducts() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Fetches product by ID
    @Override
    public ProductResponse getProductById(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with ID : " + productId
                        ));

        return mapToResponse(product);
    }

    // Updates existing product
    @Override
    public ProductResponse updateProduct(
            Integer productId,
            ProductRequest productRequest
    ) {

        Product existingProduct = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with ID : " + productId
                        ));

        // Updates entity fields
        existingProduct.setProductName(productRequest.getProductName());
        existingProduct.setUnitPrice(productRequest.getUnitPrice());
        existingProduct.setColour(productRequest.getColour());
        existingProduct.setBrand(productRequest.getBrand());
        existingProduct.setSize(productRequest.getSize());
        existingProduct.setRating(productRequest.getRating());

        Product updatedProduct =
                productRepository.save(existingProduct);

        return mapToResponse(updatedProduct);
    }

    // Deletes product by ID
    @Override
    public void deleteProduct(Integer productId) {

        Product product = productRepository.findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException(
                                "Product not found with ID : " + productId
                        ));

        productRepository.delete(product);
    }

    // Fetches products by brand
    @Override
    public List<ProductResponse> getProductsByBrand(String brand) {

        return productRepository.findByBrand(brand)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Fetches products by colour
    @Override
    public List<ProductResponse> getProductsByColour(String colour) {

        return productRepository.findByColour(colour)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Fetches products by size
    @Override
    public List<ProductResponse> getProductsBySize(String size) {

        return productRepository.findBySize(size)
                .stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    // Converts request DTO to entity
    private Product mapToEntity(ProductRequest request) {

        Product product = new Product();

        product.setProductName(request.getProductName());
        product.setUnitPrice(request.getUnitPrice());
        product.setColour(request.getColour());
        product.setBrand(request.getBrand());
        product.setSize(request.getSize());
        product.setRating(request.getRating());

        return product;
    }

    // Converts entity to response DTO
    private ProductResponse mapToResponse(Product product) {

        return new ProductResponse(
                product.getProductId(),
                product.getProductName(),
                product.getUnitPrice(),
                product.getColour(),
                product.getBrand(),
                product.getSize(),
                product.getRating()
        );
    }
}