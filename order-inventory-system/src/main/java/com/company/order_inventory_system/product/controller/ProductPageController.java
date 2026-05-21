package com.company.order_inventory_system.product.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.product.dto.ProductRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ProductPageController {

    private final EndpointExecutionService endpointExecutionService;

    @Value("${product.username}")
    private String productUsername;

    @Value("${product.password}")
    private String productPassword;

    public ProductPageController(EndpointExecutionService endpointExecutionService) {
        this.endpointExecutionService = endpointExecutionService;
    }

    // DASHBOARD
    @GetMapping("/product-module/dashboard")
    public String productDashboardPage() {
        return "fragments/product-module";
    }

    // GET ALL PRODUCTS
    @GetMapping("/product-module/endpoints/get-all-products")
    public String getAllProductsPage(Model model) {
        model.addAttribute("endpointTitle", "Get All Products");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/products");
        model.addAttribute("description", "Fetch all catalog products");
        model.addAttribute("executeUrl", "/product-module/endpoints/get-all-products/execute");
        return "endpoint-details";
    }

    @PostMapping("/product-module/endpoints/get-all-products/execute")
    public String executeGetAllProducts(Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/products",
                productUsername,
                productPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get All Products");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/products");
        model.addAttribute("description", "Fetch all catalog products");
        model.addAttribute("executeUrl", "/product-module/endpoints/get-all-products/execute");
        return "endpoint-details";
    }

    // GET PRODUCT BY ID
    @GetMapping("/product-module/endpoints/get-product-by-id")
    public String getProductByIdPage(Model model) {
        model.addAttribute("endpointTitle", "Get Product By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/products/{productId}");
        model.addAttribute("description", "Fetch product details using product ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Product ID");
        model.addAttribute("inputName", "productId");
        model.addAttribute("executeUrl", "/product-module/endpoints/get-product-by-id/execute");
        return "endpoint-details";
    }

    @PostMapping("/product-module/endpoints/get-product-by-id/execute")
    public String executeGetProductById(@RequestParam Integer productId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/products/" + productId,
                productUsername,
                productPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Product By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/products/{productId}");
        model.addAttribute("description", "Fetch product details using product ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Product ID");
        model.addAttribute("inputName", "productId");
        model.addAttribute("executeUrl", "/product-module/endpoints/get-product-by-id/execute");
        return "endpoint-details";
    }

    private List<FormField> getProductFields() {
        return List.of(
                new FormField("productName", "Product Name", "text", true),
                new FormField("unitPrice", "Unit Price", "number", true),
                new FormField("colour", "Colour", "text", true),
                new FormField("brand", "Brand", "text", true),
                new FormField("size", "Size", "text", true),
                new FormField("rating", "Rating (1 to 5)", "number", true)
        );
    }

    // CREATE PRODUCT
    @GetMapping("/product-module/endpoints/create-product")
    public String createProductPage(Model model) {
        model.addAttribute("endpointTitle", "Create Product");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/products");
        model.addAttribute("description", "Add a new product to the catalog");
        model.addAttribute("executeUrl", "/product-module/endpoints/create-product/execute");
        model.addAttribute("fields", getProductFields());
        return "endpoint-details";
    }

    @PostMapping("/product-module/endpoints/create-product/execute")
    public String executeCreateProduct(
            @RequestParam String productName,
            @RequestParam Double unitPrice,
            @RequestParam String colour,
            @RequestParam String brand,
            @RequestParam String size,
            @RequestParam Integer rating,
            Model model
    ) {
        ProductRequest request = new ProductRequest(productName, unitPrice, colour, brand, size, rating);

        Map<String, Object> response = endpointExecutionService.executePostRequest(
                "http://localhost:8080/api/products",
                request,
                productUsername,
                productPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Create Product");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/products");
        model.addAttribute("description", "Add a new product to the catalog");
        model.addAttribute("executeUrl", "/product-module/endpoints/create-product/execute");
        model.addAttribute("fields", getProductFields());
        return "endpoint-details";
    }

    // UPDATE PRODUCT
    @GetMapping("/product-module/endpoints/update-product")
    public String updateProductPage(Model model) {
        model.addAttribute("endpointTitle", "Update Product");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/products/{productId}");
        model.addAttribute("description", "Update details of an existing catalog product");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Product ID");
        model.addAttribute("inputName", "productId");
        model.addAttribute("executeUrl", "/product-module/endpoints/update-product/execute");
        model.addAttribute("fields", getProductFields());
        return "endpoint-details";
    }

    @PostMapping("/product-module/endpoints/update-product/execute")
    public String executeUpdateProduct(
            @RequestParam Integer productId,
            @RequestParam String productName,
            @RequestParam Double unitPrice,
            @RequestParam String colour,
            @RequestParam String brand,
            @RequestParam String size,
            @RequestParam Integer rating,
            Model model
    ) {
        ProductRequest request = new ProductRequest(productName, unitPrice, colour, brand, size, rating);

        Map<String, Object> response = endpointExecutionService.executePutRequest(
                "http://localhost:8080/api/products/" + productId,
                request,
                productUsername,
                productPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Update Product");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/products/{productId}");
        model.addAttribute("description", "Update details of an existing catalog product");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Product ID");
        model.addAttribute("inputName", "productId");
        model.addAttribute("executeUrl", "/product-module/endpoints/update-product/execute");
        model.addAttribute("fields", getProductFields());
        return "endpoint-details";
    }

    // DELETE PRODUCT
    @GetMapping("/product-module/endpoints/delete-product")
    public String deleteProductPage(Model model) {
        model.addAttribute("endpointTitle", "Delete Product");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/products/{productId}");
        model.addAttribute("description", "Delete product from the catalog by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Product ID");
        model.addAttribute("inputName", "productId");
        model.addAttribute("executeUrl", "/product-module/endpoints/delete-product/execute");
        return "endpoint-details";
    }

    @PostMapping("/product-module/endpoints/delete-product/execute")
    public String executeDeleteProduct(@RequestParam Integer productId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeDeleteRequest(
                "http://localhost:8080/api/products/" + productId,
                productUsername,
                productPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Delete Product");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/products/{productId}");
        model.addAttribute("description", "Delete product from the catalog by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Product ID");
        model.addAttribute("inputName", "productId");
        model.addAttribute("executeUrl", "/product-module/endpoints/delete-product/execute");
        return "endpoint-details";
    }
}
