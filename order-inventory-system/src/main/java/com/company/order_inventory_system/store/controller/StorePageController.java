package com.company.order_inventory_system.store.controller;


import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.store.dto.StoreRequestDTO;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.util.Map;

@Controller
public class StorePageController {

    private final RestTemplate restTemplate;

    private final EndpointExecutionService endpointExecutionService;

    @Value("${store.username}")
    private String storeUsername;

    @Value("${store.password}")
    private String storePassword;

    public StorePageController(RestTemplate restTemplate, EndpointExecutionService endpointExecutionService) {
        this.restTemplate = restTemplate;
        this.endpointExecutionService = endpointExecutionService;
    }

    // STORE DASHBOARD PAGE

    @GetMapping("/store-module/dashboard")
    public String storeDashboardPage() {

        return "fragments/store-module";
    }

    // GET ALL STORES PAGE
    @GetMapping("/store-module/endpoints/get-all-stores")
    public String getAllStoresPage(Model model) {

        model.addAttribute(
                "endpointTitle",
                "Get All Stores"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores"
        );

        model.addAttribute(
                "description",
                "Fetch all available stores"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/get-all-stores/execute"
        );

        return "endpoint-details";
    }

    // EXECUTE GET ALL STORES
    @PostMapping("/store-module/endpoints/get-all-stores/execute")
    public String executeGetAllStores(Model model) {

        Map<String, Object> response =

                endpointExecutionService.executeGetRequest(

                        "http://localhost:8080/api/stores",

                        storeUsername,

                        storePassword
                );

        model.addAllAttributes(response);

        model.addAttribute(
                "endpointTitle",
                "Get All Stores"
        );

        model.addAttribute(
                "description",
                "Fetch all available stores"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/get-all-stores/execute"
        );

        return "endpoint-details";
    }

    // GET STORE BY ID
    @GetMapping("/store-module/endpoints/get-store-by-id")
    public String getStoreByIdPage(Model model) {

        model.addAttribute(
                "endpointTitle",
                "Get Store By ID"
        );

        model.addAttribute(
                "description",
                "Fetch store using store ID"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/get-store-by-id/execute"
        );

        return "endpoint-details";
    }

    // Excute Method for GET STORE BY ID
    @PostMapping("/store-module/endpoints/get-store-by-id/execute")
    public String executeGetStoreById(

            @RequestParam Integer storeId,

            Model model
    ) {

        Map<String, Object> response =

                endpointExecutionService.executeGetRequest(

                        "http://localhost:8080/api/stores/" + storeId,

                        storeUsername,

                        storePassword
                );

        model.addAllAttributes(response);

        model.addAttribute(
                "endpointTitle",
                "Get Store By ID"
        );

        model.addAttribute(
                "description",
                "Fetch store using store ID"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/get-store-by-id/execute"
        );

        return "endpoint-details";
    }

    private List<FormField> getStoreFields() {
        return List.of(
                new FormField("storeName", "Store Name", "text", true),
                new FormField("webAddress", "Web Address", "text", false),
                new FormField("physicalAddress", "Physical Address", "text", false),
                new FormField("latitude", "Latitude", "number", false),
                new FormField("longitude", "Longitude", "number", false)
        );
    }

    // CREATE STORE
    @GetMapping("/store-module/endpoints/create-store")
    public String createStorePage(Model model) {

        model.addAttribute(
                "endpointTitle",
                "Create Store"
        );

        model.addAttribute(
                "description",
                "Create a new store"
        );

        model.addAttribute(
                "httpMethod",
                "POST"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/create-store/execute"
        );

        model.addAttribute(
                "fields",
                getStoreFields()
        );

        return "endpoint-details";
    }

    // Execute Method for CREATE STORE
    @PostMapping("/store-module/endpoints/create-store/execute")
    public String executeCreateStore(

            @RequestParam String storeName,

            @RequestParam(required = false)
            String webAddress,

            @RequestParam(required = false)
            String physicalAddress,

            @RequestParam(required = false)
            BigDecimal latitude,

            @RequestParam(required = false)
            BigDecimal longitude,

            Model model
    ) {

        StoreRequestDTO request = new StoreRequestDTO();

        request.setStoreName(storeName);

        request.setWebAddress(webAddress);

        request.setPhysicalAddress(physicalAddress);

        request.setLatitude(latitude);

        request.setLongitude(longitude);

        Map<String, Object> response =

                endpointExecutionService.executePostRequest(

                        "http://localhost:8080/api/stores",

                        request,

                        storeUsername,

                        storePassword
                );

        model.addAllAttributes(response);

        model.addAttribute(
                "endpointTitle",
                "Create Store"
        );

        model.addAttribute(
                "description",
                "Create a new store"
        );

        model.addAttribute(
                "httpMethod",
                "POST"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/create-store/execute"
        );

        model.addAttribute(
                "fields",
                getStoreFields()
        );

        return "endpoint-details";
    }

    // UPDATE STORE
    @GetMapping("/store-module/endpoints/update-store")
    public String updateStorePage(Model model) {

        model.addAttribute(
                "endpointTitle",
                "Update Store"
        );

        model.addAttribute(
                "description",
                "Update existing store"
        );

        model.addAttribute(
                "httpMethod",
                "PUT"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/update-store/execute"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "fields",
                getStoreFields()
        );

        return "endpoint-details";
    }

    @PostMapping("/store-module/endpoints/update-store/execute")
    public String executeUpdateStore(

            @RequestParam Integer storeId,

            @RequestParam String storeName,

            @RequestParam(required = false)
            String webAddress,

            @RequestParam(required = false)
            String physicalAddress,

            @RequestParam(required = false)
            BigDecimal latitude,

            @RequestParam(required = false)
            BigDecimal longitude,

            Model model
    ) {

        StoreRequestDTO request =
                new StoreRequestDTO();

        request.setStoreName(storeName);

        request.setWebAddress(webAddress);

        request.setPhysicalAddress(physicalAddress);

        request.setLatitude(latitude);

        request.setLongitude(longitude);

        Map<String, Object> response =

                endpointExecutionService.executePutRequest(

                        "http://localhost:8080/api/stores/" + storeId,

                        request,

                        storeUsername,

                        storePassword
                );

        model.addAllAttributes(response);

        model.addAttribute(
                "endpointTitle",
                "Update Store"
        );

        model.addAttribute(
                "description",
                "Update existing store"
        );

        model.addAttribute(
                "httpMethod",
                "PUT"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/update-store/execute"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "fields",
                getStoreFields()
        );

        return "endpoint-details";
    }

    // DELETE STORE
    @GetMapping("/store-module/endpoints/delete-store")
    public String deleteStorePage(Model model) {

        model.addAttribute(
                "endpointTitle",
                "Delete Store"
        );

        model.addAttribute(
                "description",
                "Delete store using store ID"
        );

        model.addAttribute(
                "httpMethod",
                "DELETE"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/delete-store/execute"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        return "endpoint-details";
    }

    @PostMapping("/store-module/endpoints/delete-store/execute")
    public String executeDeleteStore(

            @RequestParam Integer storeId,

            Model model
    ) {

        Map<String, Object> response =

                endpointExecutionService.executeDeleteRequest(

                        "http://localhost:8080/api/stores/" + storeId,

                        storeUsername,

                        storePassword
                );

        model.addAllAttributes(response);

        model.addAttribute(
                "endpointTitle",
                "Delete Store"
        );

        model.addAttribute(
                "description",
                "Delete store using store ID"
        );

        model.addAttribute(
                "httpMethod",
                "DELETE"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/delete-store/execute"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        return "endpoint-details";
    }

    // STORE SHIPMENTS
    @GetMapping("/store-module/endpoints/store-shipments")
    public String storeShipmentsPage(Model model) {

        model.addAttribute(
                "endpointTitle",
                "Store Shipments"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}/shipments"
        );

        model.addAttribute(
                "description",
                "Fetch all shipments for a store"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/store-shipments/execute"
        );

        return "endpoint-details";
    }

    @PostMapping("/store-module/endpoints/store-shipments/execute")
    public String executeStoreShipments(
            @RequestParam Integer storeId,
            Model model
    ) {
        Map<String, Object> response =
                endpointExecutionService.executeGetRequest(
                        "http://localhost:8080/api/stores/" + storeId + "/shipments",
                        storeUsername,
                        storePassword
                );

        model.addAllAttributes(response);

        model.addAttribute(
                "endpointTitle",
                "Store Shipments"
        );

        model.addAttribute(
                "description",
                "Fetch all shipments for a store"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}/shipments"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/store-shipments/execute"
        );

        return "endpoint-details";
    }

    // STORE ORDERS

    @GetMapping("/store-module/endpoints/store-orders")
    public String storeOrdersPage(Model model) {

        model.addAttribute(
                "endpointTitle",
                "Store Orders"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}/orders"
        );

        model.addAttribute(
                "description",
                "Fetch all orders for a store"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/store-orders/execute"
        );

        return "endpoint-details";
    }

    @PostMapping("/store-module/endpoints/store-orders/execute")
    public String executeStoreOrders(
            @RequestParam Integer storeId,
            Model model
    ) {
        Map<String, Object> response =
                endpointExecutionService.executeGetRequest(
                        "http://localhost:8080/api/stores/" + storeId + "/orders",
                        storeUsername,
                        storePassword
                );

        model.addAllAttributes(response);

        model.addAttribute(
                "endpointTitle",
                "Store Orders"
        );

        model.addAttribute(
                "description",
                "Fetch all orders for a store"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}/orders"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/store-orders/execute"
        );

        return "endpoint-details";
    }

    // STORE INVENTORY

    @GetMapping("/store-module/endpoints/store-inventory")
    public String storeInventoryPage(Model model) {

        model.addAttribute(
                "endpointTitle",
                "Store Inventory"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}/inventory"
        );

        model.addAttribute(
                "description",
                "Fetch inventory for a store"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/store-inventory/execute"
        );

        return "endpoint-details";
    }

    @PostMapping("/store-module/endpoints/store-inventory/execute")
    public String executeStoreInventory(
            @RequestParam Integer storeId,
            Model model
    ) {
        Map<String, Object> response =
                endpointExecutionService.executeGetRequest(
                        "http://localhost:8080/api/stores/" + storeId + "/inventory",
                        storeUsername,
                        storePassword
                );

        model.addAllAttributes(response);

        model.addAttribute(
                "endpointTitle",
                "Store Inventory"
        );

        model.addAttribute(
                "description",
                "Fetch inventory for a store"
        );

        model.addAttribute(
                "httpMethod",
                "GET"
        );

        model.addAttribute(
                "apiUrl",
                "/api/stores/{storeId}/inventory"
        );

        model.addAttribute(
                "requiresInput",
                true
        );

        model.addAttribute(
                "inputLabel",
                "Store ID"
        );

        model.addAttribute(
                "inputName",
                "storeId"
        );

        model.addAttribute(
                "executeUrl",
                "/store-module/endpoints/store-inventory/execute"
        );

        return "endpoint-details";
    }
}