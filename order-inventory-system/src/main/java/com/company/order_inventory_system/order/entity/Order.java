package com.company.order_inventory_system.order.entity;

import com.company.order_inventory_system.order.enums.OrderStatus;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "orders")

public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "order_id")
    private Integer orderId;

    @Column(name = "order_tms", nullable = false)
    private LocalDateTime orderTms;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "order_status", nullable = false, length = 10)
    private OrderStatus orderStatus;

    @Column(name = "store_id", nullable = false)
    private Integer storeId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public LocalDateTime getOrderTms() {
        return orderTms;
    }

    public void setOrderTms(LocalDateTime orderTms) {
        this.orderTms = orderTms;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Order(Integer orderId, LocalDateTime orderTms, Integer customerId, OrderStatus orderStatus, Integer storeId) {
        this.orderId = orderId;
        this.orderTms = orderTms;
        this.customerId = customerId;
        this.orderStatus = orderStatus;
        this.storeId = storeId;
    }

    public Order() {
    }
}