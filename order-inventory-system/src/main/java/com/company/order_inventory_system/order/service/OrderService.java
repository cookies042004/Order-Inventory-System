package com.company.order_inventory_system.order.service;

import com.company.order_inventory_system.order.dto.OrderRequest;
import com.company.order_inventory_system.order.dto.OrderResponse;
import com.company.order_inventory_system.order.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderResponse createOrder(OrderRequest request);

    List<OrderResponse> getAllOrders();

    Page<OrderResponse> getAllOrders(Pageable pageable);

    OrderResponse getOrderById(Integer orderId);

    OrderResponse updateOrder(
            Integer orderId,
            OrderRequest request);


    OrderResponse deleteOrder(
            Integer orderId);

    List<OrderResponse> getOrdersByCustomerId(
            Integer customerId);

    List<OrderResponse> getOrdersByStoreId(
            Integer storeId);

    List<OrderResponse> getOrdersByStatus(
            OrderStatus orderStatus);

    List<OrderResponse> getOrdersByDateRange(
            LocalDateTime from,
            LocalDateTime to);
    OrderResponse updateOrderStatus(
            Integer orderId,
            OrderStatus status);
}