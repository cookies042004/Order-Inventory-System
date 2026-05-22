package com.company.order_inventory_system.shipment.service;

import com.company.order_inventory_system.shipment.dto.ShipmentRequest;
import com.company.order_inventory_system.shipment.dto.ShipmentResponse;
import com.company.order_inventory_system.shipment.entity.Shipment;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import com.company.order_inventory_system.shipment.exception.ShipmentNotFoundException;
import com.company.order_inventory_system.shipment.repository.ShipmentRepository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service

public class ShipmentServiceImpl
        implements ShipmentService {

    private final ShipmentRepository shipmentRepository;

    public ShipmentServiceImpl(
            ShipmentRepository shipmentRepository) {

        this.shipmentRepository =
                shipmentRepository;
    }

    @Override
    public ShipmentResponse createShipment(
            ShipmentRequest request) {

        Shipment shipment =
                mapToEntity(request);

        Shipment savedShipment =
                shipmentRepository.save(shipment);

        return mapToResponse(savedShipment);
    }

    @Override
    public List<ShipmentResponse> getAllShipments() {

        return shipmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public Page<ShipmentResponse> getAllShipments(Pageable pageable) {
        return shipmentRepository.findAll(pageable)
                .map(this::mapToResponse);
    }

    @Override
    public ShipmentResponse getShipmentById(
            Integer shipmentId) {

        Shipment shipment =
                shipmentRepository.findById(shipmentId)
                        .orElseThrow(() ->
                                new ShipmentNotFoundException(
                                        "Shipment not found with ID: "
                                                + shipmentId));

        return mapToResponse(shipment);
    }

    @Override
    public ShipmentResponse updateShipment(
            Integer shipmentId,
            ShipmentRequest request) {

        Shipment existingShipment =
                shipmentRepository.findById(shipmentId)
                        .orElseThrow(() ->
                                new ShipmentNotFoundException(
                                        "Shipment not found with ID: "
                                                + shipmentId));

        existingShipment.setStoreId(
                request.getStoreId());

        existingShipment.setCustomerId(
                request.getCustomerId());

        existingShipment.setDeliveryAddress(
                request.getDeliveryAddress());

        existingShipment.setShipmentStatus(
                request.getShipmentStatus());

        Shipment updatedShipment =
                shipmentRepository.save(existingShipment);

        return mapToResponse(updatedShipment);
    }

    @Override
    public ShipmentResponse deleteShipment(
            Integer shipmentId) {

        Shipment existingShipment =
                shipmentRepository.findById(shipmentId)
                        .orElseThrow(() ->
                                new ShipmentNotFoundException(
                                        "Shipment not found with ID: "
                                                + shipmentId));

        ShipmentResponse response =
                mapToResponse(existingShipment);

        shipmentRepository.delete(existingShipment);

        return response;
    }

    @Override
    public List<ShipmentResponse>
    getShipmentsByCustomerId(
            Integer customerId) {

        return shipmentRepository
                .findByCustomerId(customerId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ShipmentResponse>
    getShipmentsByStoreId(
            Integer storeId) {

        return shipmentRepository
                .findByStoreId(storeId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ShipmentResponse>
    getShipmentsByStatus(
            ShipmentStatus shipmentStatus) {

        return shipmentRepository
                .findByShipmentStatus(shipmentStatus)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private Shipment mapToEntity(
            ShipmentRequest request) {

        Shipment shipment = new Shipment();

        shipment.setStoreId(
                request.getStoreId());

        shipment.setCustomerId(
                request.getCustomerId());

        shipment.setDeliveryAddress(
                request.getDeliveryAddress());

        shipment.setShipmentStatus(
                request.getShipmentStatus());

        return shipment;
    }

    private ShipmentResponse mapToResponse(
            Shipment shipment) {

        ShipmentResponse response =
                new ShipmentResponse();

        response.setShipmentId(
                shipment.getShipmentId());

        response.setStoreId(
                shipment.getStoreId());

        response.setCustomerId(
                shipment.getCustomerId());

        response.setDeliveryAddress(
                shipment.getDeliveryAddress());

        response.setShipmentStatus(
                shipment.getShipmentStatus());

        return response;
    }

    @Override
    public ShipmentResponse updateShipmentStatus(
            Integer shipmentId,
            ShipmentStatus status) {

        Shipment shipment =
                shipmentRepository.findById(shipmentId)
                        .orElseThrow(() ->
                                new ShipmentNotFoundException(
                                        "Shipment not found with ID: "
                                                + shipmentId));

        shipment.setShipmentStatus(status);

        Shipment updatedShipment =
                shipmentRepository.save(shipment);

        return mapToResponse(updatedShipment);
    }
}