package com.company.order_inventory_system.store.controller;


import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.store.dto.StoreRequestDTO;
import com.company.order_inventory_system.store.dto.StoreResponseDTO;
import com.company.order_inventory_system.store.service.StoreService;
import org.springframework.beans.factory.annotation.Value;
import java.util.List;

import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;
import org.springframework.validation.BindingResult;
import jakarta.validation.Valid;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.Map;

@Controller
public class StorePageController {

    private final RestTemplate restTemplate;

    private final EndpointExecutionService endpointExecutionService;

    private final StoreService storeService;

    @Value("${store.username}")
    private String storeUsername;

    @Value("${store.password}")
    private String storePassword;

    public StorePageController(RestTemplate restTemplate, EndpointExecutionService endpointExecutionService, StoreService storeService) {
        this.restTemplate = restTemplate;
        this.endpointExecutionService = endpointExecutionService;
        this.storeService = storeService;
    }

    // STORE DASHBOARD PAGE

    @GetMapping("/store-module/dashboard")
    public String storeDashboardPage() {

        return "fragments/store-module";
    }

    // TRADITIONAL PAGINATED CRUD VIEW

    @GetMapping("/store-module/stores")
    public String listStoresPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StoreResponseDTO> storePage = storeService.getAllStores(pageable);

        model.addAttribute("stores", storePage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", storePage.getTotalPages());
        model.addAttribute("totalItems", storePage.getTotalElements());

        return "store/list";
    }

    @GetMapping("/store-module/stores/create")
    public String createStoreForm(Model model) {
        model.addAttribute("storeForm", new StoreRequestDTO());
        return "store/create";
    }

    @PostMapping("/store-module/stores/create")
    public String processCreateStore(
            @Valid @ModelAttribute("storeForm") StoreRequestDTO storeForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        // Enforce address validation
        if ((storeForm.getWebAddress() == null || storeForm.getWebAddress().isBlank())
                && (storeForm.getPhysicalAddress() == null || storeForm.getPhysicalAddress().isBlank())) {
            bindingResult.rejectValue("webAddress", "invalid.address", "Either web address or physical address is required");
            bindingResult.rejectValue("physicalAddress", "invalid.address", "Either web address or physical address is required");
        }

        if (bindingResult.hasErrors()) {
            return "store/create";
        }

        try {
            storeService.createStore(storeForm);
            redirectAttributes.addFlashAttribute("successMessage", "Store created successfully!");
            return "redirect:/store-module/stores";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "store/create";
        }
    }

    @GetMapping("/store-module/stores/edit/{id}")
    public String editStoreForm(@PathVariable Integer id, Model model) {
        StoreResponseDTO store = storeService.getStoreById(id);
        StoreRequestDTO storeForm = new StoreRequestDTO();
        storeForm.setStoreName(store.getStoreName());
        storeForm.setWebAddress(store.getWebAddress());
        storeForm.setPhysicalAddress(store.getPhysicalAddress());
        storeForm.setLatitude(store.getLatitude());
        storeForm.setLongitude(store.getLongitude());

        model.addAttribute("storeForm", storeForm);
        model.addAttribute("storeId", id);
        return "store/edit";
    }

    @PostMapping("/store-module/stores/edit/{id}")
    public String processUpdateStore(
            @PathVariable Integer id,
            @Valid @ModelAttribute("storeForm") StoreRequestDTO storeForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        // Enforce address validation
        if ((storeForm.getWebAddress() == null || storeForm.getWebAddress().isBlank())
                && (storeForm.getPhysicalAddress() == null || storeForm.getPhysicalAddress().isBlank())) {
            bindingResult.rejectValue("webAddress", "invalid.address", "Either web address or physical address is required");
            bindingResult.rejectValue("physicalAddress", "invalid.address", "Either web address or physical address is required");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("storeId", id);
            return "store/edit";
        }

        try {
            storeService.updateStore(id, storeForm);
            redirectAttributes.addFlashAttribute("successMessage", "Store updated successfully!");
            return "redirect:/store-module/stores";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("storeId", id);
            return "store/edit";
        }
    }

    @GetMapping("/store-module/stores/delete/{id}")
    public String deleteStore(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            storeService.deleteStore(id);
            redirectAttributes.addFlashAttribute("successMessage", "Store deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/store-module/stores";
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

    // UPLOAD STORE LOGO PAGE
    @GetMapping("/store-module/endpoints/upload-store-logo")
    public String uploadStoreLogoPage(Model model) {
        model.addAttribute("endpointTitle", "Upload Store Logo");
        model.addAttribute("description", "Upload store logo binary image (.png, .jpg)");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/stores/{storeId}/logo");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/store-module/endpoints/upload-store-logo/execute");
        model.addAttribute("isMultipart", true);
        model.addAttribute("fields", List.of(
                new FormField("file", "Logo Image File", "file", true)
        ));
        return "endpoint-details";
    }

    // EXECUTE UPLOAD STORE LOGO
    @PostMapping("/store-module/endpoints/upload-store-logo/execute")
    public String executeUploadStoreLogo(
            @RequestParam Integer storeId,
            @RequestParam("file") org.springframework.web.multipart.MultipartFile file,
            Model model
    ) {
        Map<String, Object> response = endpointExecutionService.executeMultipartPostRequest(
                "http://localhost:8080/api/stores/" + storeId + "/logo",
                "file",
                file,
                storeUsername,
                storePassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Upload Store Logo");
        model.addAttribute("description", "Upload store logo binary image (.png, .jpg)");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/stores/{storeId}/logo");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/store-module/endpoints/upload-store-logo/execute");
        model.addAttribute("isMultipart", true);
        model.addAttribute("fields", List.of(
                new FormField("file", "Logo Image File", "file", true)
        ));
        return "endpoint-details";
    }

    // GET STORE LOGO PAGE
    @GetMapping("/store-module/endpoints/get-store-logo")
    public String getStoreLogoPage(Model model) {
        model.addAttribute("endpointTitle", "Get Store Logo");
        model.addAttribute("description", "Fetch and preview the store logo image");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/stores/{storeId}/logo");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/store-module/endpoints/get-store-logo/execute");
        return "endpoint-details";
    }

    // EXECUTE GET STORE LOGO
    @PostMapping("/store-module/endpoints/get-store-logo/execute")
    public String executeGetStoreLogo(
            @RequestParam Integer storeId,
            Model model
    ) {
        java.util.Map<String, Object> executionResult = new java.util.HashMap<>();
        try {
            String url = "http://localhost:8080/api/stores/" + storeId + "/logo";
            org.springframework.http.HttpHeaders headers = new org.springframework.http.HttpHeaders();
            headers.setBasicAuth(storeUsername, storePassword);
            org.springframework.http.HttpEntity<Void> entity = new org.springframework.http.HttpEntity<>(headers);
            
            org.springframework.http.ResponseEntity<byte[]> response = restTemplate.exchange(
                    url,
                    org.springframework.http.HttpMethod.GET,
                    entity,
                    byte[].class
            );
            
            executionResult.put("success", true);
            executionResult.put("statusCode", response.getStatusCode().value());
            
            byte[] body = response.getBody();
            if (body != null && body.length > 0) {
                String mimeType = response.getHeaders().getContentType() != null 
                        ? response.getHeaders().getContentType().toString() 
                        : "image/png";
                String base64Image = java.util.Base64.getEncoder().encodeToString(body);
                model.addAttribute("logoBase64", "data:" + mimeType + ";base64," + base64Image);
                
                executionResult.put("responseData", "[Binary Image Data: " + body.length + " bytes]");
                
                java.util.List<java.util.Map<String, Object>> tableData = new java.util.ArrayList<>();
                java.util.Map<String, Object> row = new java.util.LinkedHashMap<>();
                row.put("Store ID", storeId);
                row.put("MIME Type", mimeType);
                row.put("Size (Bytes)", body.length);
                tableData.add(row);
                executionResult.put("tableData", tableData);
                executionResult.put("columns", List.of("Store ID", "MIME Type", "Size (Bytes)"));
            }
        } catch (org.springframework.web.client.HttpStatusCodeException ex) {
            executionResult.put("success", false);
            executionResult.put("statusCode", ex.getStatusCode().value());
            executionResult.put("responseData", ex.getResponseBodyAsString());
            
            try {
                com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
                java.util.Map<String, Object> errorMap = mapper.readValue(
                        ex.getResponseBodyAsString(),
                        new com.fasterxml.jackson.core.type.TypeReference<java.util.Map<String, Object>>() {}
                );
                executionResult.put("errorResponse", errorMap);
            } catch (Exception e) {}
        } catch (Exception ex) {
            executionResult.put("success", false);
            executionResult.put("statusCode", 500);
            executionResult.put("responseData", ex.getMessage());
        }
        
        model.addAllAttributes(executionResult);
        model.addAttribute("endpointTitle", "Get Store Logo");
        model.addAttribute("description", "Fetch and preview the store logo image");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/stores/{storeId}/logo");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/store-module/endpoints/get-store-logo/execute");
        
        return "endpoint-details";
    }
}