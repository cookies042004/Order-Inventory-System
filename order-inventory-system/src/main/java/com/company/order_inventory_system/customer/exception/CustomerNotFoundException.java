package com.company.order_inventory_system.customer.exception;

/* Used to handle customer not found exception */
public class CustomerNotFoundException
        extends RuntimeException {

    /* Passes exception message to parent class */
    public CustomerNotFoundException(String message) {

        super(message);
    }
}