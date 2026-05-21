package com.company.order_inventory_system.shipment.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.shipment.dto.ShipmentRequest;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ShipmentPageController {

    private final EndpointExecutionService endpointExecutionService;

    @Value("${shipment.username}")
    private String shipmentUsername;

    @Value("${shipment.password}")
    private String shipmentPassword;

    public ShipmentPageController(EndpointExecutionService endpointExecutionService) {
        this.endpointExecutionService = endpointExecutionService;
    }

    // DASHBOARD
    @GetMapping("/shipment-module/dashboard")
    public String shipmentDashboardPage() {
        return "fragments/shipment-module";
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
