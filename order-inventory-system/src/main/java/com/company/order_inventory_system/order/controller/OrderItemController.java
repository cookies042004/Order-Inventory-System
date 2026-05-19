package com.company.order_inventory_system.order.controller;

import com.company.order_inventory_system.order.dto.OrderItemRequest;
import com.company.order_inventory_system.order.dto.OrderItemResponse;
import com.company.order_inventory_system.order.service.OrderItemService;

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

    @GetMapping

    public ResponseEntity<List<OrderItemResponse>>
    getAllOrderItems() {

        return ResponseEntity.ok(
                orderItemService
                        .getAllOrderItems());
    }

    @GetMapping("/{orderId}")

    public ResponseEntity<OrderItemResponse>
    getOrderItemById(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                orderItemService
                        .getOrderItemById(orderId));
    }

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

    @DeleteMapping("/{orderId}")

    public ResponseEntity<OrderItemResponse>
    deleteOrderItem(
            @PathVariable Integer orderId) {

        return ResponseEntity.ok(
                orderItemService
                        .deleteOrderItem(orderId));
    }
}