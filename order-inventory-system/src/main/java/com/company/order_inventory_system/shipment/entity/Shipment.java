package com.company.order_inventory_system.shipment.entity;

import com.company.order_inventory_system.shipment.converter.ShipmentStatusConverter;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import jakarta.persistence.*;

import java.util.Objects;

@Entity
@Table(name = "shipments")

public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column(name = "shipment_id")
    private Integer shipmentId;

    @Column(name = "store_id", nullable = false)
    private Integer storeId;

    @Column(name = "customer_id", nullable = false)
    private Integer customerId;

    @Column(name = "delivery_address", nullable = false)
    private String deliveryAddress;



    @Column(name = "shipment_status",
            nullable = false,
            length = 20)

    @Convert(converter = ShipmentStatusConverter.class)

    private ShipmentStatus shipmentStatus;

    public Shipment() {
    }

    public Shipment(Integer shipmentId,
                    Integer storeId,
                    Integer customerId,
                    String deliveryAddress,
                    ShipmentStatus shipmentStatus) {

        this.shipmentId = shipmentId;
        this.storeId = storeId;
        this.customerId = customerId;
        this.deliveryAddress = deliveryAddress;
        this.shipmentStatus = shipmentStatus;
    }

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

    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress;
    }

    public ShipmentStatus getShipmentStatus() {
        return shipmentStatus;
    }

    public void setShipmentStatus(ShipmentStatus shipmentStatus) {
        this.shipmentStatus = shipmentStatus;
    }

    @Override
    public boolean equals(Object o) {

        if (this == o) return true;

        if (o == null ||
                getClass() != o.getClass()) return false;

        Shipment shipment = (Shipment) o;

        return Objects.equals(shipmentId,
                shipment.shipmentId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(shipmentId);
    }
}