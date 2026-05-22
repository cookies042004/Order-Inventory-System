package com.company.order_inventory_system.shipment.service;

import com.company.order_inventory_system.shipment.dto.ShipmentRequest;
import com.company.order_inventory_system.shipment.dto.ShipmentResponse;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ShipmentService {

    ShipmentResponse createShipment(
            ShipmentRequest request);

    List<ShipmentResponse> getAllShipments();

    Page<ShipmentResponse> getAllShipments(Pageable pageable);

    ShipmentResponse getShipmentById(
            Integer shipmentId);

    ShipmentResponse updateShipment(
            Integer shipmentId,
            ShipmentRequest request);

    ShipmentResponse deleteShipment(
            Integer shipmentId);

    List<ShipmentResponse> getShipmentsByCustomerId(
            Integer customerId);

    List<ShipmentResponse> getShipmentsByStoreId(
            Integer storeId);

    List<ShipmentResponse> getShipmentsByStatus(
            ShipmentStatus shipmentStatus);
    ShipmentResponse updateShipmentStatus(
            Integer shipmentId,
            ShipmentStatus status);
}