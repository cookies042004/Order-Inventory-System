package com.company.order_inventory_system.customer.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.customer.dto.CustomerRequest;
import com.company.order_inventory_system.customer.dto.CustomerResponse;
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
public class CustomerPageController {

    private final EndpointExecutionService endpointExecutionService;
    private final CustomerService customerService;

    @Value("${customer.username}")
    private String customerUsername;

    @Value("${customer.password}")
    private String customerPassword;

    public CustomerPageController(EndpointExecutionService endpointExecutionService, CustomerService customerService) {
        this.endpointExecutionService = endpointExecutionService;
        this.customerService = customerService;
    }

    // CUSTOMER DASHBOARD
    @GetMapping("/customer-module/dashboard")
    public String customerDashboardPage() {
        return "fragments/customer-module";
    }

    // TRADITIONAL CRUD VIEW
    @GetMapping("/customer-module/customers")
    public String listCustomersPage(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            Model model
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<CustomerResponse> customerPage = customerService.getAllCustomers(pageable);

        model.addAttribute("customers", customerPage.getContent());
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", customerPage.getTotalPages());
        model.addAttribute("totalItems", customerPage.getTotalElements());

        return "customer/list";
    }

    @GetMapping("/customer-module/customers/create")
    public String createCustomerForm(Model model) {
        model.addAttribute("customerForm", new CustomerRequest());
        return "customer/create";
    }

    @PostMapping("/customer-module/customers/create")
    public String processCreateCustomer(
            @Valid @ModelAttribute("customerForm") CustomerRequest customerForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            return "customer/create";
        }

        try {
            customerService.createCustomer(customerForm);
            redirectAttributes.addFlashAttribute("successMessage", "Customer created successfully!");
            return "redirect:/customer-module/customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "customer/create";
        }
    }

    @GetMapping("/customer-module/customers/edit/{id}")
    public String editCustomerForm(@PathVariable Integer id, Model model) {
        CustomerResponse customer = customerService.getCustomerById(id);
        CustomerRequest customerForm = new CustomerRequest();
        customerForm.setEmailAddress(customer.getEmailAddress());
        customerForm.setFullName(customer.getFullName());

        model.addAttribute("customerForm", customerForm);
        model.addAttribute("customerId", id);
        return "customer/edit";
    }

    @PostMapping("/customer-module/customers/edit/{id}")
    public String processUpdateCustomer(
            @PathVariable Integer id,
            @Valid @ModelAttribute("customerForm") CustomerRequest customerForm,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("customerId", id);
            return "customer/edit";
        }

        try {
            customerService.updateCustomer(id, customerForm);
            redirectAttributes.addFlashAttribute("successMessage", "Customer updated successfully!");
            return "redirect:/customer-module/customers";
        } catch (Exception e) {
            model.addAttribute("errorMessage", e.getMessage());
            model.addAttribute("customerId", id);
            return "customer/edit";
        }
    }

    @GetMapping("/customer-module/customers/delete/{id}")
    public String deleteCustomer(
            @PathVariable Integer id,
            RedirectAttributes redirectAttributes
    ) {
        try {
            customerService.deleteCustomer(id);
            redirectAttributes.addFlashAttribute("successMessage", "Customer deleted successfully!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/customer-module/customers";
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
