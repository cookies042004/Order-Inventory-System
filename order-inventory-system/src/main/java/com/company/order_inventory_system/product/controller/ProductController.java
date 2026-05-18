package com.company.order_inventory_system.product.controller;

import com.company.order_inventory_system.product.dto.ProductRequest;
import com.company.order_inventory_system.product.dto.ProductResponse;
import com.company.order_inventory_system.product.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Product Controller",
        description = "APIs for managing products"
)

@RestController // Marks this class as REST controller
@RequestMapping("/api/products") // Base URL for product APIs
public class ProductController {

    private final ProductService productService;

    // Constructor injection for service layer
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // Creates and saves a new product
    @Operation(
            summary = "Create product",
            description = "Creates and saves a new product"
    )

    @ApiResponse(
            responseCode = "201",
            description = "Product created successfully"
    )

    // Creates and saves a new product
    @PostMapping

    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody ProductRequest productRequest) {

        // Calls service layer to save product
        ProductResponse createdProduct =
                productService.createProduct(productRequest);

        // Returns created product with HTTP 201
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdProduct);
    }

    // Fetches all available products
    @Operation(
            summary = "Get all products",
            description = "Returns list of all available products"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Products fetched successfully"
    )

    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        List<ProductResponse> productResponses =
                productService.getAllProducts();

        return ResponseEntity.ok(productResponses);
    }

    // Fetches product using product ID
    @Operation(
            summary = "Get product by ID",
            description = "Fetches product details using product ID"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Product fetched successfully"
    )

    @ApiResponse(
            responseCode = "404",
            description = "Product not found"
    )

    @GetMapping("/{productId}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Integer productId) {

        ProductResponse productResponse =
                productService.getProductById(productId);

        return ResponseEntity.ok(productResponse);
    }

    // Updates existing product details
    @Operation(
            summary = "Update product",
            description = "Updates product using product ID"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Product updated successfully"
    )

    @ApiResponse(
            responseCode = "404",
            description = "Product not found"
    )

    @PutMapping("/{productId}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Integer productId,

            @Valid @RequestBody
            ProductRequest productRequest) {

        ProductResponse updatedProduct =
                productService.updateProduct(
                        productId,
                        productRequest
                );

        return ResponseEntity.ok(updatedProduct);
    }

    // Deletes product using product ID
    @Operation(
            summary = "Delete product",
            description = "Deletes product using product ID"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Product deleted successfully"
    )

    @ApiResponse(
            responseCode = "404",
            description = "Product not found"
    )

    // Deletes product by ID
    @DeleteMapping("/{productId}")
    public ResponseEntity<ProductResponse> deleteProduct(
            @PathVariable Integer productId) {

        // Delete product and fetch deleted details
        ProductResponse deletedProduct =
                productService.deleteProduct(productId);

        return ResponseEntity.ok(deletedProduct);
    }
}