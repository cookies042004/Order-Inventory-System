package com.company.order_inventory_system.order.controller;

import com.company.order_inventory_system.order.dto.OrderItemRequest;
import com.company.order_inventory_system.order.dto.OrderItemResponse;
import com.company.order_inventory_system.order.service.OrderItemService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order-items")
public class OrderItemController {

    private final OrderItemService orderItemService;

    public OrderItemController(
            OrderItemService orderItemService) {

        this.orderItemService =
                orderItemService;
    }

    @Operation(summary = "Create a new order item")
    @PostMapping
    public ResponseEntity<OrderItemResponse>
    createOrderItem(

            @Valid
            @RequestBody
            OrderItemRequest request) {

        OrderItemResponse response =
                orderItemService
                        .createOrderItem(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @Operation(summary = "Get all order items")
    @GetMapping
    public ResponseEntity<List<OrderItemResponse>>
    getAllOrderItems() {

        return ResponseEntity.ok(
                orderItemService
                        .getAllOrderItems());
    }

    @Operation(summary = "Get order item by ID")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderItemResponse>
    getOrderItemById(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                orderItemService
                        .getOrderItemById(orderId));
    }

    @Operation(summary = "Update order item details")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderItemResponse>
    updateOrderItem(

            @PathVariable Integer orderId,

            @Valid
            @RequestBody
            OrderItemRequest request) {

        return ResponseEntity.ok(
                orderItemService
                        .updateOrderItem(
                                orderId,
                                request));
    }

    @Operation(summary = "Delete order item by ID")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String>
    deleteOrderItem(
            @PathVariable Integer orderId) {

        orderItemService
                .deleteOrderItem(orderId);

        return ResponseEntity.ok(
                "Order item deleted successfully");
    }
}