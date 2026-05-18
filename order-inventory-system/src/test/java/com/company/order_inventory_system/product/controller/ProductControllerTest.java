package com.company.order_inventory_system.product.controller;

import com.company.order_inventory_system.product.dto.ProductRequest;
import com.company.order_inventory_system.product.dto.ProductResponse;
import com.company.order_inventory_system.product.service.ProductService;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.test.context.bean.override.mockito.MockitoBean;

import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ProductController.class)

public class ProductControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProductService productService;

    @Autowired
    private ObjectMapper objectMapper;

    // Tests product creation API
    @Test
    void testCreateProduct() throws Exception {

        // Sample request object
        ProductRequest request = new ProductRequest(
                "Nike Shoes",
                4999.0,
                "BLACK",
                "NIKE",
                "M",
                5
        );

        // Sample response object
        ProductResponse response = new ProductResponse(
                1,
                "Nike Shoes",
                4999.0,
                "BLACK",
                "NIKE",
                "M",
                5
        );

        // Mock service response
        when(productService.createProduct(any(ProductRequest.class)))
                .thenReturn(response);

        // Perform POST request and validate response
        mockMvc.perform(post("/api/products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.productName")
                        .value("Nike Shoes"));
    }

    // Tests fetch product by ID API
    @Test
    void testGetProductById() throws Exception {

        // Sample response object
        ProductResponse response = new ProductResponse(
                1,
                "Nike Shoes",
                4999.0,
                "BLACK",
                "NIKE",
                "M",
                5
        );

        // Mock service response
        when(productService.getProductById(1))
                .thenReturn(response);

        // Perform GET request and validate response
        mockMvc.perform(get("/api/products/1"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.productName")
                        .value("Nike Shoes"));
    }

    // Tests fetch all products API
    @Test
    void testGetAllProducts() throws Exception {

        // Sample response list
        List<ProductResponse> responseList = List.of(
                new ProductResponse(
                        1,
                        "Nike Shoes",
                        4999.0,
                        "BLACK",
                        "NIKE",
                        "M",
                        5
                )
        );

        // Mock service response
        when(productService.getAllProducts())
                .thenReturn(responseList);

        // Perform GET request and validate response
        mockMvc.perform(get("/api/products"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$[0].productName")
                        .value("Nike Shoes"));
    }

    // Tests delete product API
    @Test
    void testDeleteProduct() throws Exception {

        // Sample deleted product response
        ProductResponse response = new ProductResponse(
                1,
                "Nike Shoes",
                4999.0,
                "BLACK",
                "NIKE",
                "M",
                5
        );

        // Mock service response
        when(productService.deleteProduct(1))
                .thenReturn(response);

        // Perform DELETE request and validate response
        mockMvc.perform(delete("/api/products/1"))

                .andExpect(status().isOk())

                .andExpect(jsonPath("$.productName")
                        .value("Nike Shoes"));
    }
}