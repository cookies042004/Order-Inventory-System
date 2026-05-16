package com.company.order_inventory_system.shipment.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ShipmentStatus {

    CREATED("CREATED"),
    SHIPPED("SHIPPED"),
    IN_TRANSIT("IN-TRANSIT"),
    DELIVERED("DELIVERED");

    private final String value;

    ShipmentStatus(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static ShipmentStatus fromValue(String value) {

        for (ShipmentStatus status : ShipmentStatus.values()) {

            if (status.value.equalsIgnoreCase(value)) {
                return status;
            }
        }

        throw new IllegalArgumentException(
                "Invalid shipment status: " + value);
    }
}