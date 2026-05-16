package com.company.order_inventory_system.order.service;

import com.company.order_inventory_system.order.dto.OrderItemRequest;
import com.company.order_inventory_system.order.dto.OrderItemResponse;

import java.util.List;

public interface OrderItemService {

    OrderItemResponse createOrderItem(
            OrderItemRequest request);

    List<OrderItemResponse> getAllOrderItems();

    OrderItemResponse getOrderItemById(
            Integer orderId);

    OrderItemResponse updateOrderItem(
            Integer orderId,
            OrderItemRequest request);

    void deleteOrderItem(Integer orderId);
}