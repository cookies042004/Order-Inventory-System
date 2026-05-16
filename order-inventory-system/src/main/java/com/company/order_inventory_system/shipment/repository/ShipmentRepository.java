package com.company.order_inventory_system.shipment.repository;

import com.company.order_inventory_system.shipment.entity.Shipment;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShipmentRepository
        extends JpaRepository<Shipment, Integer> {

    List<Shipment> findByCustomerId(Integer customerId);

    List<Shipment> findByStoreId(Integer storeId);

    List<Shipment> findByShipmentStatus(
            ShipmentStatus shipmentStatus);
}