package com.company.order_inventory_system.product.controller;

import com.company.order_inventory_system.product.dto.ProductRequest;
import com.company.order_inventory_system.product.dto.ProductResponse;

import com.company.order_inventory_system.product.service.ProductService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController // Marks this class as REST API controller
@RequestMapping("/api/products") // Base URL for product APIs
public class ProductController {

    private final ProductService productService;

    // Constructor injection for service layer
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Creates and saves new product
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest) {

        ProductResponse savedProduct =
                productService.createProduct(productRequest);

        return new ResponseEntity<>(
                savedProduct,
                HttpStatus.CREATED
        );
    }

    // Fetches all products
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        List<ProductResponse> products =
                productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    // Fetches product using product ID
    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Integer productId) {

        ProductResponse product =
                productService.getProductById(productId);

        return ResponseEntity.ok(product);
    }

    // Updates existing product details
    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Integer productId,
            @Valid @RequestBody ProductRequest productRequest) {

        ProductResponse updatedProduct =
                productService.updateProduct(
                        productId,
                        productRequest
                );

        return ResponseEntity.ok(updatedProduct);
    }

    // Deletes product using product ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Integer productId) {

        productService.deleteProduct(productId);

        return ResponseEntity.ok(
                "Product deleted successfully"
        );
    }

    // Fetches products by brand
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<ProductResponse>>
    getProductsByBrand(@PathVariable String brand) {

        List<ProductResponse> products =
                productService.getProductsByBrand(brand);

        return ResponseEntity.ok(products);
    }

    // Fetches products by colour
    @GetMapping("/colour/{colour}")
    public ResponseEntity<List<ProductResponse>>
    getProductsByColour(@PathVariable String colour) {

        List<ProductResponse> products =
                productService.getProductsByColour(colour);

        return ResponseEntity.ok(products);
    }

    // Fetches products by size
    @GetMapping("/size/{size}")
    public ResponseEntity<List<ProductResponse>>
    getProductsBySize(@PathVariable String size) {

        List<ProductResponse> products =
                productService.getProductsBySize(size);

        return ResponseEntity.ok(products);
    }
}