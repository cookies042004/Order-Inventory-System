package com.company.order_inventory_system.order.repository;

import com.company.order_inventory_system.order.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OrderItemRepository extends JpaRepository<OrderItem, Integer> {

    List<OrderItem> findByOrderId(Integer orderId);

    Optional<OrderItem> findByOrderIdAndProductId(Integer orderId, Integer productId);

    Optional<OrderItem> findByOrderIdAndLineItemId(Integer orderId, Integer lineItemId);
}