package com.company.order_inventory_system.order.exception;

public class OrderItemNotFoundException
        extends RuntimeException {

    public OrderItemNotFoundException(
            String message) {

        super(message);
    }
}