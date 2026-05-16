package com.company.order_inventory_system.shipment.dto;

import com.company.order_inventory_system.shipment.enums.ShipmentStatus;

public class ShipmentResponse {

    private Integer shipmentId;

    private Integer storeId;

    private Integer customerId;

    private String deliveryAddress;

    private ShipmentStatus shipmentStatus;

    public Integer getShipmentId() {
        return shipmentId;
    }

    public void setShipmentId(Integer shipmentId) {
        this.shipmentId = shipmentId;
    }

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