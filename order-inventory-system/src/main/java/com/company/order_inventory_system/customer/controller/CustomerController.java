package com.company.order_inventory_system.customer.controller;

import com.company.order_inventory_system.customer.dto.CustomerRequest;
import com.company.order_inventory_system.customer.dto.CustomerResponse;

import com.company.order_inventory_system.customer.service.CustomerService;

import com.company.order_inventory_system.order.entity.Order;
import com.company.order_inventory_system.order.repository.OrderRepository;

import com.company.order_inventory_system.shipment.entity.Shipment;
import com.company.order_inventory_system.shipment.repository.ShipmentRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.annotation.Validated;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Tag(
        name = "Customer Controller",
        description = "APIs for managing customers"
)

@RestController

@RequestMapping("/api/customers")

@Validated

public class CustomerController {

    /* Service layer for customer operations */
    private final CustomerService customerService;

    /* Repository for linked order data */
    private final OrderRepository orderRepository;

    /* Repository for linked shipment data */
    private final ShipmentRepository shipmentRepository;

    /* Constructor injection */
    public CustomerController(
            CustomerService customerService,
            OrderRepository orderRepository,
            ShipmentRepository shipmentRepository) {

        this.customerService =
                customerService;

        this.orderRepository =
                orderRepository;

        this.shipmentRepository =
                shipmentRepository;
    }

    /* Creates new customer */
    @Operation(
            summary = "Create customer",
            description = "Creates and saves new customer"
    )

    @ApiResponse(
            responseCode = "201",
            description = "Customer created successfully"
    )

    @PostMapping

    public ResponseEntity<Map<String, Object>>
    createCustomer(

            @Valid

            @RequestBody

            CustomerRequest customerRequest) {

        CustomerResponse savedCustomer =
                customerService.createCustomer(
                        customerRequest
                );

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "message",
                "Customer created successfully"
        );

        response.put(
                "data",
                savedCustomer
        );

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED
        );
    }

    /* Fetches all customers */
    @Operation(
            summary = "Get all customers",
            description = "Returns list of all customers"
    )

    @GetMapping

    public ResponseEntity<Map<String, Object>>
    getAllCustomers() {

        List<CustomerResponse> customerResponses =
                customerService
                        .getAllCustomers();

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "message",
                "All customers fetched successfully"
        );

        response.put(
                "data",
                customerResponses
        );

        return ResponseEntity.ok(
                response
        );
    }

    /* Fetches customer by ID */
    @Operation(
            summary = "Get customer by ID",
            description =
                    "Fetches customer using customer ID"
    )

    @GetMapping("/{customerId}")

    public ResponseEntity<Map<String, Object>>
    getCustomerById(

            @PathVariable
            Integer customerId) {

        CustomerResponse customerResponse =
                customerService.getCustomerById(
                        customerId
                );

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "message",
                "Customer fetched successfully"
        );

        response.put(
                "data",
                customerResponse
        );

        return ResponseEntity.ok(
                response
        );
    }

    /* Updates customer details */
    @Operation(
            summary = "Update customer",
            description =
                    "Updates customer using customer ID"
    )

    @PutMapping("/{customerId}")

    public ResponseEntity<Map<String, Object>>
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

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "message",
                "Customer updated successfully"
        );

        response.put(
                "data",
                updatedCustomer
        );

        return ResponseEntity.ok(
                response
        );
    }

    /* Deletes customer */
    @Operation(
            summary = "Delete customer",
            description =
                    "Deletes customer using customer ID"
    )

    @DeleteMapping("/{customerId}")

    public ResponseEntity<Map<String, Object>>
    deleteCustomer(

            @PathVariable
            Integer customerId) {

        CustomerResponse deletedCustomer =
                customerService.deleteCustomer(
                        customerId
                );

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "message",
                "Customer deleted successfully"
        );

        response.put(
                "data",
                deletedCustomer
        );

        return ResponseEntity.ok(
                response
        );
    }

    /* Fetches customer using email */
    @Operation(
            summary = "Get customer by email",
            description =
                    "Fetches customer using email address"
    )

    @GetMapping("/search")

    public ResponseEntity<Map<String, Object>>
    getCustomerByEmail(

            @RequestParam

            @NotBlank(
                    message = "Email cannot be empty"
            )

            @Email(
                    message = "Invalid email format"
            )

            @Size(
                    max = 255,
                    message =
                            "Email cannot exceed 255 characters"
            )

            String email) {

        CustomerResponse customerResponse =
                customerService
                        .getCustomerByEmail(
                                email
                        );

        Map<String, Object> response =
                new HashMap<>();

        response.put(
                "message",
                "Customer fetched successfully using email"
        );

        response.put(
                "data",
                customerResponse
        );

        return ResponseEntity.ok(
                response
        );
    }

    /* Fetches linked customer orders */
    @Operation(
            summary = "Get customer orders",
            description =
                    "Fetches orders linked to customer"
    )

    @GetMapping("/{customerId}/orders")

    public ResponseEntity<Map<String, Object>>
    getCustomerOrders(

            @PathVariable
            Integer customerId) {

        List<Order> orders =
                orderRepository
                        .findByCustomerId(
                                customerId
                        );

        Map<String, Object> response =
                new HashMap<>();

        if (orders.isEmpty()) {

            response.put(
                    "message",
                    "No orders linked to customer"
            );

            response.put(
                    "customerId",
                    customerId
            );

            return ResponseEntity.ok(
                    response
            );
        }

        response.put(
                "message",
                "Orders fetched successfully"
        );

        response.put(
                "data",
                orders
        );

        return ResponseEntity.ok(
                response
        );
    }

    /* Fetches linked customer shipments */
    @Operation(
            summary = "Get customer shipments",
            description =
                    "Fetches shipments linked to customer"
    )

    @GetMapping("/{customerId}/shipments")

    public ResponseEntity<Map<String, Object>>
    getCustomerShipments(

            @PathVariable
            Integer customerId) {

        List<Shipment> shipments =
                shipmentRepository
                        .findByCustomerId(
                                customerId
                        );

        Map<String, Object> response =
                new HashMap<>();

        if (shipments.isEmpty()) {

            response.put(
                    "message",
                    "No shipments linked to customer"
            );

            response.put(
                    "customerId",
                    customerId
            );

            return ResponseEntity.ok(
                    response
            );
        }

        response.put(
                "message",
                "Shipments fetched successfully"
        );

        response.put(
                "data",
                shipments
        );

        return ResponseEntity.ok(
                response
        );
    }
}