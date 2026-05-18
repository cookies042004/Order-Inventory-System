package com.company.order_inventory_system.customer.controller;

import com.company.order_inventory_system.customer.dto.CustomerRequest;
import com.company.order_inventory_system.customer.dto.CustomerResponse;

import com.company.order_inventory_system.customer.service.CustomerService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(
        name = "Customer Controller",
        description = "APIs for managing customers"
)

@RestController // Marks this class as REST controller
@RequestMapping("/api/customers") // Base URL for customer APIs

public class CustomerController {

    private final CustomerService customerService;

    // Constructor injection for service layer
    public CustomerController(
            CustomerService customerService) {

        this.customerService =
                customerService;
    }

    // Creates and saves new customer
    @Operation(
            summary = "Create customer",
            description = "Creates and saves new customer"
    )

    @ApiResponse(
            responseCode = "201",
            description = "Customer created successfully"
    )

    @PostMapping
    public ResponseEntity<CustomerResponse>
    createCustomer(

            @Valid
            @RequestBody
            CustomerRequest customerRequest) {

        CustomerResponse savedCustomer =
                customerService.createCustomer(
                        customerRequest
                );

        return new ResponseEntity<>(
                savedCustomer,
                HttpStatus.CREATED
        );
    }

    // Fetches all customers
    @Operation(
            summary = "Get all customers",
            description = "Returns list of all customers"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Customers fetched successfully"
    )

    @GetMapping
    public ResponseEntity<
            List<CustomerResponse>>
    getAllCustomers() {

        List<CustomerResponse>
                customerResponses =

                customerService
                        .getAllCustomers();

        return ResponseEntity.ok(
                customerResponses
        );
    }

    // Fetches customer using customer ID
    @Operation(
            summary = "Get customer by ID",
            description =
                    "Fetches customer using customer ID"
    )

    @ApiResponse(
            responseCode = "200",
            description =
                    "Customer fetched successfully"
    )

    @ApiResponse(
            responseCode = "404",
            description = "Customer not found"
    )

    @GetMapping("/{customerId}")

    public ResponseEntity<CustomerResponse>
    getCustomerById(

            @PathVariable
            Integer customerId) {

        CustomerResponse customerResponse =
                customerService.getCustomerById(
                        customerId
                );

        return ResponseEntity.ok(
                customerResponse
        );
    }

    // Updates existing customer details
    @Operation(
            summary = "Update customer",
            description =
                    "Updates customer using customer ID"
    )

    @ApiResponse(
            responseCode = "200",
            description =
                    "Customer updated successfully"
    )

    @ApiResponse(
            responseCode = "404",
            description = "Customer not found"
    )

    @PutMapping("/{customerId}")

    public ResponseEntity<CustomerResponse>
    updateCustomer(

            @PathVariable
            Integer customerId,

            @Valid
            @RequestBody
            CustomerRequest customerRequest) {

        CustomerResponse updatedCustomer =
                customerService.updateCustomer(
                        customerId,
                        customerRequest
                );

        return ResponseEntity.ok(
                updatedCustomer
        );
    }

    // Deletes customer using customer ID
    @Operation(
            summary = "Delete customer",
            description =
                    "Deletes customer using customer ID"
    )

    @ApiResponse(
            responseCode = "200",
            description =
                    "Customer deleted successfully"
    )

    @ApiResponse(
            responseCode = "404",
            description = "Customer not found"
    )

    @DeleteMapping("/{customerId}")

    public ResponseEntity<String>
    deleteCustomer(

            @PathVariable
            Integer customerId) {

        customerService.deleteCustomer(
                customerId
        );

        return ResponseEntity.ok(
                "Customer deleted successfully"
        );
    }

    // Fetches customer using email address
    @Operation(
            summary = "Get customer by email",
            description =
                    "Fetches customer using email address"
    )

    @ApiResponse(
            responseCode = "200",
            description =
                    "Customer fetched successfully"
    )

    @ApiResponse(
            responseCode = "404",
            description = "Customer not found"
    )

    @GetMapping("/search")

    public ResponseEntity<CustomerResponse>
    getCustomerByEmail(

            @RequestParam
            String email) {

        CustomerResponse customerResponse =
                customerService
                        .getCustomerByEmail(
                                email
                        );

        return ResponseEntity.ok(
                customerResponse
        );
    }

    // Fetches orders linked to customer
    @Operation(
            summary = "Get customer orders",
            description =
                    "Fetches orders linked to customer"
    )

    @ApiResponse(
            responseCode = "200",
            description =
                    "Customer orders fetched successfully"
    )

    @GetMapping("/{customerId}/orders")

    public ResponseEntity<String>
    getCustomerOrders(

            @PathVariable
            Integer customerId) {

        return ResponseEntity.ok(
                "Orders linked to customer ID: "
                        + customerId
        );
    }

    // Fetches shipments linked to customer
    @Operation(
            summary = "Get customer shipments",
            description =
                    "Fetches shipments linked to customer"
    )

    @ApiResponse(
            responseCode = "200",
            description =
                    "Customer shipments fetched successfully"
    )

    @GetMapping("/{customerId}/shipments")

    public ResponseEntity<String>
    getCustomerShipments(

            @PathVariable
            Integer customerId) {

        return ResponseEntity.ok(
                "Shipments linked to customer ID: "
                        + customerId
        );
    }
}