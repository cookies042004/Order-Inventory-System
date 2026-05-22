package com.company.order_inventory_system.shipment.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.shipment.dto.ShipmentRequest;
import com.company.order_inventory_system.shipment.dto.ShipmentResponse;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import com.company.order_inventory_system.shipment.service.ShipmentService;
import com.company.order_inventory_system.store.service.StoreService;
import com.company.order_inventory_system.customer.service.CustomerService;
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
public class ShipmentPageController {

    private final EndpointExecutionService endpointExecutionService;
    private final ShipmentService shipmentService;
    private final StoreService storeService;
    private final CustomerService customerService;

    @Value("${shipment.username}")
    private String shipmentUsername;

    @Value("${shipment.password}")
    private String shipmentPassword;

    public ShipmentPageController(EndpointExecutionService endpointExecutionService,
                                  ShipmentService shipmentService,
                                  StoreService storeService,
                                  CustomerService customerService) {
        this.endpointExecutionService = endpointExecutionService;
        this.shipmentService = shipmentService;
        this.storeService = storeService;
        this.customerService = customerService;
    }

    // DASHBOARD
    @GetMapping("/shipment-module/dashboard")
    public String shipmentDashboardPage() {
        return "fragments/shipment-module";
    }

    // TRADITIONAL CRUD VIEW
    @GetMapping("/shipment-module/shipments")
    public String listShipmentsPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ShipmentResponse> shipmentPage = shipmentService.getAllShipments(pageable);

        model.addAttribute("shipments", shipmentPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", shipmentPage.getTotalPages());
        model.addAttribute("totalItems", shipmentPage.getTotalElements());

        return "shipment/list";
    }

    @GetMapping("/shipment-module/shipments/create")
    public String createShipmentForm(Model model) {
        model.addAttribute("shipmentForm", new ShipmentRequest());
        model.addAttribute("stores", storeService.getAllStores());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("statuses", ShipmentStatus.values());
        return "shipment/create";
    }

    @PostMapping("/shipment-module/shipments/create")
    public String processCreateShipment(
            @Valid @ModelAttribute("shipmentForm") ShipmentRequest shipmentForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("stores", storeService.getAllStores());
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("statuses", ShipmentStatus.values());
            return "shipment/create";
        }

        try {
            shipmentService.createShipment(shipmentForm);
            redirectAttributes.addFlashAttribute("successMessage", "Shipment created successfully!");
            return "redirect:/shipment-module/shipments";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("stores", storeService.getAllStores());
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("statuses", ShipmentStatus.values());
            return "shipment/create";
        }
    }

    @GetMapping("/shipment-module/shipments/edit/{id}")
    public String editShipmentForm(@PathVariable Integer id, Model model) {
        ShipmentResponse shipment = shipmentService.getShipmentById(id);
        ShipmentRequest shipmentForm = new ShipmentRequest();
        shipmentForm.setCustomerId(shipment.getCustomerId());
        shipmentForm.setStoreId(shipment.getStoreId());
        shipmentForm.setDeliveryAddress(shipment.getDeliveryAddress());
        shipmentForm.setShipmentStatus(shipment.getShipmentStatus());

        model.addAttribute("shipmentForm", shipmentForm);
        model.addAttribute("shipmentId", id);
        model.addAttribute("stores", storeService.getAllStores());
        model.addAttribute("customers", customerService.getAllCustomers());
        model.addAttribute("statuses", ShipmentStatus.values());
        return "shipment/edit";
    }

    @PostMapping("/shipment-module/shipments/edit/{id}")
    public String processUpdateShipment(
            @PathVariable Integer id,
            @Valid @ModelAttribute("shipmentForm") ShipmentRequest shipmentForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("shipmentId", id);
            model.addAttribute("stores", storeService.getAllStores());
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("statuses", ShipmentStatus.values());
            return "shipment/edit";
        }

        try {
            shipmentService.updateShipment(id, shipmentForm);
            redirectAttributes.addFlashAttribute("successMessage", "Shipment updated successfully!");
            return "redirect:/shipment-module/shipments";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("shipmentId", id);
            model.addAttribute("stores", storeService.getAllStores());
            model.addAttribute("customers", customerService.getAllCustomers());
            model.addAttribute("statuses", ShipmentStatus.values());
            return "shipment/edit";
        }
    }

    @GetMapping("/shipment-module/shipments/delete/{id}")
    public String deleteShipment(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            shipmentService.deleteShipment(id);
            redirectAttributes.addFlashAttribute("successMessage", "Shipment deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/shipment-module/shipments";
    }

    // GET ALL SHIPMENTS
    @GetMapping("/shipment-module/endpoints/get-all-shipments")
    public String getAllShipmentsPage(Model model) {
        model.addAttribute("endpointTitle", "Get All Shipments");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments");
        model.addAttribute("description", "Fetch all shipment and delivery records");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-all-shipments/execute");
        return "endpoint-details";
    }

    @PostMapping("/shipment-module/endpoints/get-all-shipments/execute")
    public String executeGetAllShipments(Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/shipments",
                shipmentUsername,
                shipmentPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get All Shipments");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments");
        model.addAttribute("description", "Fetch all shipment and delivery records");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-all-shipments/execute");
        return "endpoint-details";
    }

    // GET SHIPMENT BY ID
    @GetMapping("/shipment-module/endpoints/get-shipment-by-id")
    public String getShipmentByIdPage(Model model) {
        model.addAttribute("endpointTitle", "Get Shipment By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments/{shipmentId}");
        model.addAttribute("description", "Fetch shipment details using shipment ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Shipment ID");
        model.addAttribute("inputName", "shipmentId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-shipment-by-id/execute");
        return "endpoint-details";
    }

    @PostMapping("/shipment-module/endpoints/get-shipment-by-id/execute")
    public String executeGetShipmentById(@RequestParam Integer shipmentId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/shipments/" + shipmentId,
                shipmentUsername,
                shipmentPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Shipment By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments/{shipmentId}");
        model.addAttribute("description", "Fetch shipment details using shipment ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Shipment ID");
        model.addAttribute("inputName", "shipmentId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-shipment-by-id/execute");
        return "endpoint-details";
    }

    private List<FormField> getShipmentFields() {
        return List.of(
                new FormField("storeId", "Store ID", "number", true),
                new FormField("customerId", "Customer ID", "number", true),
                new FormField("deliveryAddress", "Delivery Address", "text", true),
                new FormField("shipmentStatus", "Shipment Status", "select", true, List.of("CREATED", "SHIPPED", "IN-TRANSIT", "DELIVERED"))
        );
    }

    // CREATE SHIPMENT
    @GetMapping("/shipment-module/endpoints/create-shipment")
    public String createShipmentPage(Model model) {
        model.addAttribute("endpointTitle", "Create Shipment");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/shipments");
        model.addAttribute("description", "Register a new shipment for delivery");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/create-shipment/execute");
        model.addAttribute("fields", getShipmentFields());
        return "endpoint-details";
    }

    @PostMapping("/shipment-module/endpoints/create-shipment/execute")
    public String executeCreateShipment(
            @RequestParam Integer storeId,
            @RequestParam Integer customerId,
            @RequestParam String deliveryAddress,
            @RequestParam String shipmentStatus,
            Model model
    ) {
        ShipmentRequest request = new ShipmentRequest();
        request.setStoreId(storeId);
        request.setCustomerId(customerId);
        request.setDeliveryAddress(deliveryAddress);
        request.setShipmentStatus(ShipmentStatus.fromValue(shipmentStatus));

        Map<String, Object> response = endpointExecutionService.executePostRequest(
                "http://localhost:8080/api/shipments",
                request,
                shipmentUsername,
                shipmentPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Create Shipment");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/shipments");
        model.addAttribute("description", "Register a new shipment for delivery");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/create-shipment/execute");
        model.addAttribute("fields", getShipmentFields());
        return "endpoint-details";
    }

    // UPDATE SHIPMENT
    @GetMapping("/shipment-module/endpoints/update-shipment")
    public String updateShipmentPage(Model model) {
        model.addAttribute("endpointTitle", "Update Shipment");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/shipments/{shipmentId}");
        model.addAttribute("description", "Update details of an existing shipment");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Shipment ID");
        model.addAttribute("inputName", "shipmentId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/update-shipment/execute");
        model.addAttribute("fields", getShipmentFields());
        return "endpoint-details";
    }

    @PostMapping("/shipment-module/endpoints/update-shipment/execute")
    public String executeUpdateShipment(
            @RequestParam Integer shipmentId,
            @RequestParam Integer storeId,
            @RequestParam Integer customerId,
            @RequestParam String deliveryAddress,
            @RequestParam String shipmentStatus,
            Model model
    ) {
        ShipmentRequest request = new ShipmentRequest();
        request.setStoreId(storeId);
        request.setCustomerId(customerId);
        request.setDeliveryAddress(deliveryAddress);
        request.setShipmentStatus(ShipmentStatus.fromValue(shipmentStatus));

        Map<String, Object> response = endpointExecutionService.executePutRequest(
                "http://localhost:8080/api/shipments/" + shipmentId,
                request,
                shipmentUsername,
                shipmentPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Update Shipment");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/shipments/{shipmentId}");
        model.addAttribute("description", "Update details of an existing shipment");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Shipment ID");
        model.addAttribute("inputName", "shipmentId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/update-shipment/execute");
        model.addAttribute("fields", getShipmentFields());
        return "endpoint-details";
    }

    // DELETE SHIPMENT
    @GetMapping("/shipment-module/endpoints/delete-shipment")
    public String deleteShipmentPage(Model model) {
        model.addAttribute("endpointTitle", "Delete Shipment");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/shipments/{shipmentId}");
        model.addAttribute("description", "Delete a shipment record by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Shipment ID");
        model.addAttribute("inputName", "shipmentId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/delete-shipment/execute");
        return "endpoint-details";
    }

    @PostMapping("/shipment-module/endpoints/delete-shipment/execute")
    public String executeDeleteShipment(@RequestParam Integer shipmentId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeDeleteRequest(
                "http://localhost:8080/api/shipments/" + shipmentId,
                shipmentUsername,
                shipmentPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Delete Shipment");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/shipments/{shipmentId}");
        model.addAttribute("description", "Delete a shipment record by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Shipment ID");
        model.addAttribute("inputName", "shipmentId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/delete-shipment/execute");
        return "endpoint-details";
    }

    // GET SHIPMENTS BY CUSTOMER
    @GetMapping("/shipment-module/endpoints/get-shipments-by-customer")
    public String getShipmentsByCustomerPage(Model model) {
        model.addAttribute("endpointTitle", "Get Shipments By Customer");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments/customer/{customerId}");
        model.addAttribute("description", "Fetch all shipments dispatched to a customer");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-shipments-by-customer/execute");
        return "endpoint-details";
    }

    @PostMapping("/shipment-module/endpoints/get-shipments-by-customer/execute")
    public String executeGetShipmentsByCustomer(@RequestParam Integer customerId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/shipments/customer/" + customerId,
                shipmentUsername,
                shipmentPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Shipments By Customer");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments/customer/{customerId}");
        model.addAttribute("description", "Fetch all shipments dispatched to a customer");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-shipments-by-customer/execute");
        return "endpoint-details";
    }

    // GET SHIPMENTS BY STORE
    @GetMapping("/shipment-module/endpoints/get-shipments-by-store")
    public String getShipmentsByStorePage(Model model) {
        model.addAttribute("endpointTitle", "Get Shipments By Store");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments/store/{storeId}");
        model.addAttribute("description", "Fetch all shipments originating from a specific store");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-shipments-by-store/execute");
        return "endpoint-details";
    }

    @PostMapping("/shipment-module/endpoints/get-shipments-by-store/execute")
    public String executeGetShipmentsByStore(@RequestParam Integer storeId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/shipments/store/" + storeId,
                shipmentUsername,
                shipmentPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Shipments By Store");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments/store/{storeId}");
        model.addAttribute("description", "Fetch all shipments originating from a specific store");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-shipments-by-store/execute");
        return "endpoint-details";
    }

    // GET SHIPMENTS BY STATUS
    @GetMapping("/shipment-module/endpoints/get-shipments-by-status")
    public String getShipmentsByStatusPage(Model model) {
        List<FormField> fields = List.of(
                new FormField("status", "Shipment Status", "select", true, List.of("CREATED", "SHIPPED", "IN-TRANSIT", "DELIVERED"))
        );
        model.addAttribute("endpointTitle", "Get Shipments By Status");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments/status/{status}");
        model.addAttribute("description", "Filter and fetch shipments by delivery status");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-shipments-by-status/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    @PostMapping("/shipment-module/endpoints/get-shipments-by-status/execute")
    public String executeGetShipmentsByStatus(@RequestParam String status, Model model) {
        List<FormField> fields = List.of(
                new FormField("status", "Shipment Status", "select", true, List.of("CREATED", "SHIPPED", "IN-TRANSIT", "DELIVERED"))
        );
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/shipments/status/" + status,
                shipmentUsername,
                shipmentPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Shipments By Status");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/shipments/status/{status}");
        model.addAttribute("description", "Filter and fetch shipments by delivery status");
        model.addAttribute("executeUrl", "/shipment-module/endpoints/get-shipments-by-status/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }
}
