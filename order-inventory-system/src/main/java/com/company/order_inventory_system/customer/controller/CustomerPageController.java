package com.company.order_inventory_system.customer.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.customer.dto.CustomerRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class CustomerPageController {

    private final EndpointExecutionService endpointExecutionService;

    @Value("${customer.username}")
    private String customerUsername;

    @Value("${customer.password}")
    private String customerPassword;

    public CustomerPageController(EndpointExecutionService endpointExecutionService) {
        this.endpointExecutionService = endpointExecutionService;
    }

    // CUSTOMER DASHBOARD
    @GetMapping("/customer-module/dashboard")
    public String customerDashboardPage() {
        return "fragments/customer-module";
    }

    // GET ALL CUSTOMERS
    @GetMapping("/customer-module/endpoints/get-all-customers")
    public String getAllCustomersPage(Model model) {
        model.addAttribute("endpointTitle", "Get All Customers");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers");
        model.addAttribute("description", "Fetch all registered customers");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-all-customers/execute");
        return "endpoint-details";
    }

    @PostMapping("/customer-module/endpoints/get-all-customers/execute")
    public String executeGetAllCustomers(Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/customers",
                customerUsername,
                customerPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get All Customers");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers");
        model.addAttribute("description", "Fetch all registered customers");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-all-customers/execute");
        return "endpoint-details";
    }

    // GET CUSTOMER BY ID
    @GetMapping("/customer-module/endpoints/get-customer-by-id")
    public String getCustomerByIdPage(Model model) {
        model.addAttribute("endpointTitle", "Get Customer By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers/{customerId}");
        model.addAttribute("description", "Fetch a customer profile using their ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-customer-by-id/execute");
        return "endpoint-details";
    }

    @PostMapping("/customer-module/endpoints/get-customer-by-id/execute")
    public String executeGetCustomerById(@RequestParam Integer customerId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/customers/" + customerId,
                customerUsername,
                customerPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Customer By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers/{customerId}");
        model.addAttribute("description", "Fetch a customer profile using their ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-customer-by-id/execute");
        return "endpoint-details";
    }

    private List<FormField> getCustomerFields() {
        return List.of(
                new FormField("emailAddress", "Email Address", "email", true),
                new FormField("fullName", "Full Name", "text", true)
        );
    }

    // CREATE CUSTOMER
    @GetMapping("/customer-module/endpoints/create-customer")
    public String createCustomerPage(Model model) {
        model.addAttribute("endpointTitle", "Create Customer");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/customers");
        model.addAttribute("description", "Create a new customer profile");
        model.addAttribute("executeUrl", "/customer-module/endpoints/create-customer/execute");
        model.addAttribute("fields", getCustomerFields());
        return "endpoint-details";
    }

    @PostMapping("/customer-module/endpoints/create-customer/execute")
    public String executeCreateCustomer(
            @RequestParam String emailAddress,
            @RequestParam String fullName,
            Model model
    ) {
        CustomerRequest request = new CustomerRequest();
        request.setEmailAddress(emailAddress);
        request.setFullName(fullName);

        Map<String, Object> response = endpointExecutionService.executePostRequest(
                "http://localhost:8080/api/customers",
                request,
                customerUsername,
                customerPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Create Customer");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/customers");
        model.addAttribute("description", "Create a new customer profile");
        model.addAttribute("executeUrl", "/customer-module/endpoints/create-customer/execute");
        model.addAttribute("fields", getCustomerFields());
        return "endpoint-details";
    }

    // UPDATE CUSTOMER
    @GetMapping("/customer-module/endpoints/update-customer")
    public String updateCustomerPage(Model model) {
        model.addAttribute("endpointTitle", "Update Customer");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/customers/{customerId}");
        model.addAttribute("description", "Update an existing customer profile");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/update-customer/execute");
        model.addAttribute("fields", getCustomerFields());
        return "endpoint-details";
    }

    @PostMapping("/customer-module/endpoints/update-customer/execute")
    public String executeUpdateCustomer(
            @RequestParam Integer customerId,
            @RequestParam String emailAddress,
            @RequestParam String fullName,
            Model model
    ) {
        CustomerRequest request = new CustomerRequest();
        request.setEmailAddress(emailAddress);
        request.setFullName(fullName);

        Map<String, Object> response = endpointExecutionService.executePutRequest(
                "http://localhost:8080/api/customers/" + customerId,
                request,
                customerUsername,
                customerPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Update Customer");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/customers/{customerId}");
        model.addAttribute("description", "Update an existing customer profile");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/update-customer/execute");
        model.addAttribute("fields", getCustomerFields());
        return "endpoint-details";
    }

    // DELETE CUSTOMER
    @GetMapping("/customer-module/endpoints/delete-customer")
    public String deleteCustomerPage(Model model) {
        model.addAttribute("endpointTitle", "Delete Customer");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/customers/{customerId}");
        model.addAttribute("description", "Delete a customer profile by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/delete-customer/execute");
        return "endpoint-details";
    }

    @PostMapping("/customer-module/endpoints/delete-customer/execute")
    public String executeDeleteCustomer(@RequestParam Integer customerId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeDeleteRequest(
                "http://localhost:8080/api/customers/" + customerId,
                customerUsername,
                customerPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Delete Customer");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/customers/{customerId}");
        model.addAttribute("description", "Delete a customer profile by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/delete-customer/execute");
        return "endpoint-details";
    }

    // GET CUSTOMER BY EMAIL
    @GetMapping("/customer-module/endpoints/get-customer-by-email")
    public String getCustomerByEmailPage(Model model) {
        List<FormField> fields = List.of(
                new FormField("email", "Email Address", "email", true)
        );
        model.addAttribute("endpointTitle", "Search Customer by Email");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers/search?email={email}");
        model.addAttribute("description", "Search customer by email address");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-customer-by-email/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    @PostMapping("/customer-module/endpoints/get-customer-by-email/execute")
    public String executeGetCustomerByEmail(@RequestParam String email, Model model) {
        List<FormField> fields = List.of(
                new FormField("email", "Email Address", "email", true)
        );
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/customers/search?email=" + email,
                customerUsername,
                customerPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Search Customer by Email");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers/search?email={email}");
        model.addAttribute("description", "Search customer by email address");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-customer-by-email/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    // GET CUSTOMER ORDERS
    @GetMapping("/customer-module/endpoints/get-customer-orders")
    public String getCustomerOrdersPage(Model model) {
        model.addAttribute("endpointTitle", "Get Customer Orders");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers/{customerId}/orders");
        model.addAttribute("description", "Fetch all orders associated with a customer");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-customer-orders/execute");
        return "endpoint-details";
    }

    @PostMapping("/customer-module/endpoints/get-customer-orders/execute")
    public String executeGetCustomerOrders(@RequestParam Integer customerId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/customers/" + customerId + "/orders",
                customerUsername,
                customerPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Customer Orders");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers/{customerId}/orders");
        model.addAttribute("description", "Fetch all orders associated with a customer");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-customer-orders/execute");
        return "endpoint-details";
    }

    // GET CUSTOMER SHIPMENTS
    @GetMapping("/customer-module/endpoints/get-customer-shipments")
    public String getCustomerShipmentsPage(Model model) {
        model.addAttribute("endpointTitle", "Get Customer Shipments");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers/{customerId}/shipments");
        model.addAttribute("description", "Fetch all shipments associated with a customer");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-customer-shipments/execute");
        return "endpoint-details";
    }

    @PostMapping("/customer-module/endpoints/get-customer-shipments/execute")
    public String executeGetCustomerShipments(@RequestParam Integer customerId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/customers/" + customerId + "/shipments",
                customerUsername,
                customerPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Customer Shipments");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/customers/{customerId}/shipments");
        model.addAttribute("description", "Fetch all shipments associated with a customer");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/customer-module/endpoints/get-customer-shipments/execute");
        return "endpoint-details";
    }
}
