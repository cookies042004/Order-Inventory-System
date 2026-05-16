package com.company.order_inventory_system.shipment.exception;

public class ShipmentNotFoundException
        extends RuntimeException {

    public ShipmentNotFoundException(
            String message) {

        super(message);
    }
}