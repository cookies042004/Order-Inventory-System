package com.company.order_inventory_system.order.service;

import com.company.order_inventory_system.order.dto.OrderItemRequest;
import com.company.order_inventory_system.order.dto.OrderItemResponse;
import com.company.order_inventory_system.order.entity.OrderItem;
import com.company.order_inventory_system.order.exception.OrderItemNotFoundException;
import com.company.order_inventory_system.order.repository.OrderItemRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class OrderItemServiceImpl
        implements OrderItemService {

    private final OrderItemRepository
            orderItemRepository;

    public OrderItemServiceImpl(
            OrderItemRepository orderItemRepository) {

        this.orderItemRepository =
                orderItemRepository;
    }

    @Override
    public OrderItemResponse createOrderItem(
            OrderItemRequest request) {

        OrderItem orderItem =
                mapToEntity(request);

        OrderItem savedItem =
                orderItemRepository.save(orderItem);

        return mapToResponse(savedItem);
    }

    @Override
    public List<OrderItemResponse>
    getAllOrderItems() {

        return orderItemRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderItemResponse getOrderItemById(
            Integer orderId) {

        OrderItem orderItem =
                orderItemRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderItemNotFoundException(
                                        "Order item not found with ID: "
                                                + orderId));

        return mapToResponse(orderItem);
    }

    @Override
    public OrderItemResponse updateOrderItem(
            Integer orderId,
            OrderItemRequest request) {

        OrderItem existingItem =
                orderItemRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderItemNotFoundException(
                                        "Order item not found with ID: "
                                                + orderId));

        existingItem.setLineItemId(
                request.getLineItemId());

        existingItem.setProductId(
                request.getProductId());

        existingItem.setUnitPrice(
                request.getUnitPrice());

        existingItem.setQuantity(
                request.getQuantity());

        existingItem.setShipmentId(
                request.getShipmentId());

        OrderItem updatedItem =
                orderItemRepository.save(existingItem);

        return mapToResponse(updatedItem);
    }

    @Override
    public void deleteOrderItem(
            Integer orderId) {

        OrderItem existingItem =
                orderItemRepository.findById(orderId)
                        .orElseThrow(() ->
                                new OrderItemNotFoundException(
                                        "Order item not found with ID: "
                                                + orderId));

        orderItemRepository.delete(existingItem);
    }

    private OrderItem mapToEntity(
            OrderItemRequest request) {

        OrderItem item = new OrderItem();

        item.setLineItemId(
                request.getLineItemId());

        item.setProductId(
                request.getProductId());

        item.setUnitPrice(
                request.getUnitPrice());

        item.setQuantity(
                request.getQuantity());

        item.setShipmentId(
                request.getShipmentId());

        return item;
    }

    private OrderItemResponse mapToResponse(
            OrderItem item) {

        OrderItemResponse response =
                new OrderItemResponse();

        response.setOrderId(
                item.getOrderId());

        response.setLineItemId(
                item.getLineItemId());

        response.setProductId(
                item.getProductId());

        response.setUnitPrice(
                item.getUnitPrice());

        response.setQuantity(
                item.getQuantity());

        response.setShipmentId(
                item.getShipmentId());

        return response;
    }
}