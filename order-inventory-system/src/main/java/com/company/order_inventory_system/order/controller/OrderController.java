package com.company.order_inventory_system.order.controller;

import com.company.order_inventory_system.order.dto.OrderRequest;
import com.company.order_inventory_system.order.dto.OrderResponse;
import com.company.order_inventory_system.order.enums.OrderStatus;
import com.company.order_inventory_system.order.service.OrderService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(
            OrderService orderService) {

        this.orderService = orderService;
    }

    @Operation(summary = "Create a new order")
    @PostMapping
    public ResponseEntity<OrderResponse>
    createOrder(
            @Valid
            @RequestBody
            OrderRequest request) {

        OrderResponse response =
                orderService.createOrder(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get all orders")
    @GetMapping
    public ResponseEntity<List<OrderResponse>>
    getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders());
    }

    @Operation(summary = "Get order by order ID")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponse>
    getOrderById(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                orderService.getOrderById(orderId));
    }

    @Operation(summary = "Update order details")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderResponse>
    updateOrder(
            @PathVariable Integer orderId,

            @Valid
            @RequestBody
            OrderRequest request) {

        return ResponseEntity.ok(
                orderService.updateOrder(
                        orderId,
                        request));
    }

    @Operation(summary = "Delete order by ID")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String>
    deleteOrder(
            @PathVariable Integer orderId) {

        orderService.deleteOrder(orderId);

        return ResponseEntity.ok(
                "Order deleted successfully");
    }

    @Operation(summary = "Get orders by customer ID")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<OrderResponse>>
    getOrdersByCustomerId(
            @PathVariable Integer customerId) {

        return ResponseEntity.ok(
                orderService
                        .getOrdersByCustomerId(
                                customerId));
    }

    @Operation(summary = "Get orders by store ID")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<OrderResponse>>
    getOrdersByStoreId(
            @PathVariable Integer storeId) {

        return ResponseEntity.ok(
                orderService
                        .getOrdersByStoreId(
                                storeId));
    }

    @Operation(summary = "Get orders by status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderResponse>>
    getOrdersByStatus(
            @PathVariable OrderStatus status) {

        return ResponseEntity.ok(
                orderService
                        .getOrdersByStatus(status));
    }

    @Operation(summary = "Get orders between start and end date")
    @GetMapping("/daterange")
    public ResponseEntity<List<OrderResponse>>
    getOrdersByDateRange(

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime start,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME)
            LocalDateTime end) {

        return ResponseEntity.ok(
                orderService
                        .getOrdersByDateRange(
                                start,
                                end));
    }
}