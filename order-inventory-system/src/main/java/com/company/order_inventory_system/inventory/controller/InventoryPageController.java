package com.company.order_inventory_system.inventory.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.inventory.dto.InventoryRequestDTO;
import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.inventory.service.InventoryService;
import com.company.order_inventory_system.store.service.StoreService;
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
public class InventoryPageController {

    private final EndpointExecutionService endpointExecutionService;
    private final InventoryService inventoryService;
    private final StoreService storeService;
    private final ProductService productService;

    @Value("${inventory.username}")
    private String inventoryUsername;

    @Value("${inventory.password}")
    private String inventoryPassword;

    public InventoryPageController(EndpointExecutionService endpointExecutionService,
                                   InventoryService inventoryService,
                                   StoreService storeService,
                                   ProductService productService) {
        this.endpointExecutionService = endpointExecutionService;
        this.inventoryService = inventoryService;
        this.storeService = storeService;
        this.productService = productService;
    }

    // DASHBOARD
    @GetMapping("/inventory-module/dashboard")
    public String inventoryDashboardPage() {
        return "fragments/inventory-module";
    }

    // TRADITIONAL CRUD VIEW
    @GetMapping("/inventory-module/inventory")
    public String listInventoryPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<InventoryResponseDTO> inventoryPage = inventoryService.getAllInventory(pageable);

        model.addAttribute("inventoryList", inventoryPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", inventoryPage.getTotalPages());
        model.addAttribute("totalItems", inventoryPage.getTotalElements());

        return "inventory/list";
    }

    @GetMapping("/inventory-module/inventory/create")
    public String createInventoryForm(Model model) {
        model.addAttribute("inventoryForm", new InventoryRequestDTO());
        model.addAttribute("stores", storeService.getAllStores());
        model.addAttribute("products", productService.getAllProducts());
        return "inventory/create";
    }

    @PostMapping("/inventory-module/inventory/create")
    public String processCreateInventory(
            @Valid @ModelAttribute("inventoryForm") InventoryRequestDTO inventoryForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("stores", storeService.getAllStores());
            model.addAttribute("products", productService.getAllProducts());
            return "inventory/create";
        }

        try {
            inventoryService.createInventory(inventoryForm);
            redirectAttributes.addFlashAttribute("successMessage", "Inventory record created successfully!");
            return "redirect:/inventory-module/inventory";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("stores", storeService.getAllStores());
            model.addAttribute("products", productService.getAllProducts());
            return "inventory/create";
        }
    }

    @GetMapping("/inventory-module/inventory/edit/{id}")
    public String editInventoryForm(@PathVariable Integer id, Model model) {
        InventoryResponseDTO inventory = inventoryService.getInventoryById(id);
        InventoryRequestDTO inventoryForm = new InventoryRequestDTO();
        inventoryForm.setStoreId(inventory.getStoreId());
        inventoryForm.setProductId(inventory.getProductId());
        inventoryForm.setProductInventory(inventory.getProductInventory());

        model.addAttribute("inventoryForm", inventoryForm);
        model.addAttribute("inventoryId", id);
        model.addAttribute("stores", storeService.getAllStores());
        model.addAttribute("products", productService.getAllProducts());
        return "inventory/edit";
    }

    @PostMapping("/inventory-module/inventory/edit/{id}")
    public String processUpdateInventory(
            @PathVariable Integer id,
            @Valid @ModelAttribute("inventoryForm") InventoryRequestDTO inventoryForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("inventoryId", id);
            model.addAttribute("stores", storeService.getAllStores());
            model.addAttribute("products", productService.getAllProducts());
            return "inventory/edit";
        }

        try {
            inventoryService.updateInventory(id, inventoryForm);
            redirectAttributes.addFlashAttribute("successMessage", "Inventory record updated successfully!");
            return "redirect:/inventory-module/inventory";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("inventoryId", id);
            model.addAttribute("stores", storeService.getAllStores());
            model.addAttribute("products", productService.getAllProducts());
            return "inventory/edit";
        }
    }

    @GetMapping("/inventory-module/inventory/delete/{id}")
    public String deleteInventory(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            inventoryService.deleteInventory(id);
            redirectAttributes.addFlashAttribute("successMessage", "Inventory record deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/inventory-module/inventory";
    }

    // GET ALL INVENTORY
    @GetMapping("/inventory-module/endpoints/get-all-inventory")
    public String getAllInventoryPage(Model model) {
        model.addAttribute("endpointTitle", "Get All Inventory");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/inventory");
        model.addAttribute("description", "Fetch all product inventory/stock records across all stores");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/get-all-inventory/execute");
        return "endpoint-details";
    }

    @PostMapping("/inventory-module/endpoints/get-all-inventory/execute")
    public String executeGetAllInventory(Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/inventory",
                inventoryUsername,
                inventoryPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get All Inventory");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/inventory");
        model.addAttribute("description", "Fetch all product inventory/stock records across all stores");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/get-all-inventory/execute");
        return "endpoint-details";
    }

    // GET INVENTORY BY ID
    @GetMapping("/inventory-module/endpoints/get-inventory-by-id")
    public String getInventoryByIdPage(Model model) {
        model.addAttribute("endpointTitle", "Get Inventory By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/inventory/{inventoryId}");
        model.addAttribute("description", "Fetch inventory details using inventory ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Inventory ID");
        model.addAttribute("inputName", "inventoryId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/get-inventory-by-id/execute");
        return "endpoint-details";
    }

    @PostMapping("/inventory-module/endpoints/get-inventory-by-id/execute")
    public String executeGetInventoryById(@RequestParam Integer inventoryId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/inventory/" + inventoryId,
                inventoryUsername,
                inventoryPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Inventory By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/inventory/{inventoryId}");
        model.addAttribute("description", "Fetch inventory details using inventory ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Inventory ID");
        model.addAttribute("inputName", "inventoryId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/get-inventory-by-id/execute");
        return "endpoint-details";
    }

    private List<FormField> getInventoryFields() {
        return List.of(
                new FormField("storeId", "Store ID", "number", true),
                new FormField("productId", "Product ID", "number", true),
                new FormField("productInventory", "Product Inventory Quantity", "number", true)
        );
    }

    // CREATE INVENTORY
    @GetMapping("/inventory-module/endpoints/create-inventory")
    public String createInventoryPage(Model model) {
        model.addAttribute("endpointTitle", "Create Inventory");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/inventory");
        model.addAttribute("description", "Add a new stock allocation level for a product in a store");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/create-inventory/execute");
        model.addAttribute("fields", getInventoryFields());
        return "endpoint-details";
    }

    @PostMapping("/inventory-module/endpoints/create-inventory/execute")
    public String executeCreateInventory(
            @RequestParam Integer storeId,
            @RequestParam Integer productId,
            @RequestParam Integer productInventory,
            Model model
    ) {
        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setStoreId(storeId);
        request.setProductId(productId);
        request.setProductInventory(productInventory);

        Map<String, Object> response = endpointExecutionService.executePostRequest(
                "http://localhost:8080/api/inventory",
                request,
                inventoryUsername,
                inventoryPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Create Inventory");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/inventory");
        model.addAttribute("description", "Add a new stock allocation level for a product in a store");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/create-inventory/execute");
        model.addAttribute("fields", getInventoryFields());
        return "endpoint-details";
    }

    // UPDATE INVENTORY
    @GetMapping("/inventory-module/endpoints/update-inventory")
    public String updateInventoryPage(Model model) {
        model.addAttribute("endpointTitle", "Update Inventory");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/inventory/{inventoryId}");
        model.addAttribute("description", "Update an existing inventory stock level record");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Inventory ID");
        model.addAttribute("inputName", "inventoryId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/update-inventory/execute");
        model.addAttribute("fields", getInventoryFields());
        return "endpoint-details";
    }

    @PostMapping("/inventory-module/endpoints/update-inventory/execute")
    public String executeUpdateInventory(
            @RequestParam Integer inventoryId,
            @RequestParam Integer storeId,
            @RequestParam Integer productId,
            @RequestParam Integer productInventory,
            Model model
    ) {
        InventoryRequestDTO request = new InventoryRequestDTO();
        request.setStoreId(storeId);
        request.setProductId(productId);
        request.setProductInventory(productInventory);

        Map<String, Object> response = endpointExecutionService.executePutRequest(
                "http://localhost:8080/api/inventory/" + inventoryId,
                request,
                inventoryUsername,
                inventoryPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Update Inventory");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/inventory/{inventoryId}");
        model.addAttribute("description", "Update an existing inventory stock level record");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Inventory ID");
        model.addAttribute("inputName", "inventoryId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/update-inventory/execute");
        model.addAttribute("fields", getInventoryFields());
        return "endpoint-details";
    }

    // DELETE INVENTORY
    @GetMapping("/inventory-module/endpoints/delete-inventory")
    public String deleteInventoryPage(Model model) {
        model.addAttribute("endpointTitle", "Delete Inventory");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/inventory/{inventoryId}");
        model.addAttribute("description", "Delete inventory record by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Inventory ID");
        model.addAttribute("inputName", "inventoryId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/delete-inventory/execute");
        return "endpoint-details";
    }

    @PostMapping("/inventory-module/endpoints/delete-inventory/execute")
    public String executeDeleteInventory(@RequestParam Integer inventoryId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeDeleteRequest(
                "http://localhost:8080/api/inventory/" + inventoryId,
                inventoryUsername,
                inventoryPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Delete Inventory");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/inventory/{inventoryId}");
        model.addAttribute("description", "Delete inventory record by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Inventory ID");
        model.addAttribute("inputName", "inventoryId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/delete-inventory/execute");
        return "endpoint-details";
    }

    // GET INVENTORY BY STORE ID
    @GetMapping("/inventory-module/endpoints/get-inventory-by-store")
    public String getInventoryByStorePage(Model model) {
        model.addAttribute("endpointTitle", "Get Inventory By Store");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/inventory/store/{storeId}");
        model.addAttribute("description", "Fetch all inventory stock allocations for a store");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/get-inventory-by-store/execute");
        return "endpoint-details";
    }

    @PostMapping("/inventory-module/endpoints/get-inventory-by-store/execute")
    public String executeGetInventoryByStore(@RequestParam Integer storeId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/inventory/store/" + storeId,
                inventoryUsername,
                inventoryPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Inventory By Store");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/inventory/store/{storeId}");
        model.addAttribute("description", "Fetch all inventory stock allocations for a store");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/get-inventory-by-store/execute");
        return "endpoint-details";
    }

    // GET INVENTORY BY PRODUCT ID
    @GetMapping("/inventory-module/endpoints/get-inventory-by-product")
    public String getInventoryByProductPage(Model model) {
        model.addAttribute("endpointTitle", "Get Inventory By Product");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/inventory/product/{productId}");
        model.addAttribute("description", "Fetch stock levels of a product across all stores");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Product ID");
        model.addAttribute("inputName", "productId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/get-inventory-by-product/execute");
        return "endpoint-details";
    }

    @PostMapping("/inventory-module/endpoints/get-inventory-by-product/execute")
    public String executeGetInventoryByProduct(@RequestParam Integer productId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/inventory/product/" + productId,
                inventoryUsername,
                inventoryPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Inventory By Product");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/inventory/product/{productId}");
        model.addAttribute("description", "Fetch stock levels of a product across all stores");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Product ID");
        model.addAttribute("inputName", "productId");
        model.addAttribute("executeUrl", "/inventory-module/endpoints/get-inventory-by-product/execute");
        return "endpoint-details";
    }
}
