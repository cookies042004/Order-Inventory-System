package com.company.order_inventory_system.product.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.product.dto.ProductRequest;
import com.company.order_inventory_system.product.dto.ProductResponse;
import com.company.order_inventory_system.product.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

@Controller
public class ProductPageController {

    private final EndpointExecutionService endpointExecutionService;
    private final ProductService productService;

    @Value("${product.username}")
    private String productUsername;

    @Value("${product.password}")
    private String productPassword;

    //constructor injection -Spring automatically injects these objects

    public ProductPageController(EndpointExecutionService endpointExecutionService, ProductService productService) {
        this.endpointExecutionService = endpointExecutionService;
        this.productService = productService;
    }

    // DASHBOARD
    @GetMapping("/product-module/dashboard")
    public String productDashboardPage() {
        return "fragments/product-module";
    }

    // TRADITIONAL CRUD VIEW
    @GetMapping("/product-module/products")
    public String listProductsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ProductResponse> productPage = productService.getAllProducts(pageable);

        model.addAttribute("products", productPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productPage.getTotalPages());
        model.addAttribute("totalItems", productPage.getTotalElements());

        return "product/list";
    }

    @GetMapping("/product-module/products/create")
    public String createProductForm(Model model) {
        model.addAttribute("productForm", new ProductRequest());
        return "product/create";
    }

    @PostMapping("/product-module/products/create")
    public String processCreateProduct(
            //transfer data from Controller to View(HTML page)
            @Valid @ModelAttribute("productForm") ProductRequest productForm,
            //Stores validation errors
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "product/create";
        }

        try {
            productService.createProduct(productForm);
            redirectAttributes.addFlashAttribute("successMessage", "Product created successfully!");
            return "redirect:/product-module/products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "product/create";
        }
    }

    @GetMapping("/product-module/products/edit/{id}")
    public String editProductForm(@PathVariable Integer id, Model model) {
        ProductResponse product = productService.getProductById(id);
        ProductRequest productForm = new ProductRequest();
        productForm.setProductName(product.getProductName());
        productForm.setUnitPrice(product.getUnitPrice());
        productForm.setColour(product.getColour());
        productForm.setBrand(product.getBrand());
        productForm.setSize(product.getSize());
        productForm.setRating(product.getRating());

        model.addAttribute("productForm", productForm);
        model.addAttribute("productId", id);
        return "product/edit";
    }

    @PostMapping("/product-module/products/edit/{id}")
    public String processUpdateProduct(
            @PathVariable Integer id,
            @Valid @ModelAttribute("productForm") ProductRequest productForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("productId", id);
            return "product/edit";
        }

        try {
            productService.updateProduct(id, productForm);
            redirectAttributes.addFlashAttribute("successMessage", "Product updated successfully!");
            return "redirect:/product-module/products";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("productId", id);
            return "product/edit";
        }
    }

    @GetMapping("/product-module/products/delete/{id}")
    public String deleteProduct(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            productService.deleteProduct(id);
            redirectAttributes.addFlashAttribute("successMessage", "Product deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/product-module/products";
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
