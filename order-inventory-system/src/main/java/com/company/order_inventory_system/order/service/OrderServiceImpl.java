package com.company.order_inventory_system.order.service;

import com.company.order_inventory_system.order.dto.OrderRequest;
import com.company.order_inventory_system.order.dto.OrderResponse;
import com.company.order_inventory_system.order.entity.Order;
import com.company.order_inventory_system.order.enums.OrderStatus;
import com.company.order_inventory_system.order.exception.OrderNotFoundException;
import com.company.order_inventory_system.order.repository.OrderRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service

public class OrderServiceImpl
        implements OrderService {

    private final OrderRepository orderRepository;

    public OrderServiceImpl(
            OrderRepository orderRepository) {

        this.orderRepository = orderRepository;
    }

    @Override
    public OrderResponse createOrder(
            OrderRequest request) {

        Order order = mapToEntity(request);

        Order savedOrder =
                orderRepository.save(order);

        return mapToResponse(savedOrder);
    }

    @Override
    public List<OrderResponse> getAllOrders() {

        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse getOrderById(
            Integer orderId) {

        Order order =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order not found with ID: "
                                                + orderId));

        return mapToResponse(order);
    }

    @Override
    public OrderResponse updateOrder(
            Integer orderId,
            OrderRequest request) {

        Order existingOrder =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order not found with ID: "
                                                + orderId));

        existingOrder.setOrderTms(
                request.getOrderTms());

        existingOrder.setCustomerId(
                request.getCustomerId());

        existingOrder.setOrderStatus(
                request.getOrderStatus());

        existingOrder.setStoreId(
                request.getStoreId());

        Order updatedOrder =
                orderRepository.save(existingOrder);

        return mapToResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Integer orderId) {

        Order existingOrder =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order not found with ID: "
                                                + orderId));

        orderRepository.delete(existingOrder);
    }

    @Override
    public List<OrderResponse> getOrdersByCustomerId(
            Integer customerId) {

        return orderRepository.findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getOrdersByStoreId(
            Integer storeId) {

        return orderRepository.findByStoreId(storeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getOrdersByStatus(
            OrderStatus orderStatus) {

        return orderRepository.findByOrderStatus(orderStatus)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<OrderResponse> getOrdersByDateRange(
            LocalDateTime from,
            LocalDateTime to) {

        return orderRepository
                .findByOrderTmsBetween(from, to)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Order mapToEntity(OrderRequest request) {

        Order order = new Order();

        order.setOrderTms(request.getOrderTms());
        order.setCustomerId(request.getCustomerId());
        order.setOrderStatus(request.getOrderStatus());
        order.setStoreId(request.getStoreId());

        return order;
    }

    private OrderResponse mapToResponse(
            Order order) {

        OrderResponse response =
                new OrderResponse();

        response.setOrderId(order.getOrderId());
        response.setOrderTms(order.getOrderTms());
        response.setCustomerId(order.getCustomerId());
        response.setOrderStatus(order.getOrderStatus());
        response.setStoreId(order.getStoreId());

        return response;
    }
    @Override
    public OrderResponse updateOrderStatus(
            Integer orderId,
            OrderStatus status) {

        Order existingOrder =
                orderRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderNotFoundException(
                                        "Order not found with ID: "
                                                + orderId));

        existingOrder.setOrderStatus(status);

        Order updatedOrder =
                orderRepository.save(existingOrder);

        return mapToResponse(updatedOrder);
    }
}