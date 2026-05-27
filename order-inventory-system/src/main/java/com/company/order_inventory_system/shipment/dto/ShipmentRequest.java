package com.company.order_inventory_system.shipment.dto;

import com.company.order_inventory_system.shipment.enums.ShipmentStatus;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ShipmentRequest {

    @NotNull(message = "Store ID is required")
    private Integer storeId;

    @NotNull(message = "Customer ID is required")
    private Integer customerId;

    @NotBlank(message = "Delivery address is required")
    private String deliveryAddress;

    @NotNull(message = "Shipment status is required")
    private ShipmentStatus shipmentStatus;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    public void setDeliveryAddress(
            String deliveryAddress) {

        this.deliveryAddress = deliveryAddress;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(
            ShipmentStatus shipmentStatus) {

        this.shipmentStatus = shipmentStatus;
    }
}