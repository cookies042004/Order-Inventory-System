package com.company.order_inventory_system.order.exception;

public class OrderNotFoundException
        extends RuntimeException {

    public OrderNotFoundException(String message) {
        super(message);
    }
}