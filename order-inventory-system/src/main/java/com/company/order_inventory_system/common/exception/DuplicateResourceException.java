package com.company.order_inventory_system.common.exception;

public class DuplicateResourceException extends RuntimeException {
    public DuplicateResourceException(String message){
        super(message);
    }
}
