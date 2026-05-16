package com.company.order_inventory_system.order.repository;

import com.company.order_inventory_system.order.entity.Order;
import com.company.order_inventory_system.order.enums.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findByCustomerId(Integer customerId);

    List<Order> findByStoreId(Integer storeId);

    List<Order> findByOrderStatus(OrderStatus orderStatus);

    List<Order> findByOrderTmsBetween(LocalDateTime from, LocalDateTime to);
}