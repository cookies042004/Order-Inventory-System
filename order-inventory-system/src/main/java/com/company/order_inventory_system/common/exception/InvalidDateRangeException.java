package com.company.order_inventory_system.common.exception;

public class InvalidDateRangeException
        extends RuntimeException {

    public InvalidDateRangeException(
            String message) {

        super(message);
    }
}