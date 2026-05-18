package com.company.order_inventory_system.shipment.controller;

import com.company.order_inventory_system.shipment.dto.ShipmentRequest;
import com.company.order_inventory_system.shipment.dto.ShipmentResponse;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import com.company.order_inventory_system.shipment.service.ShipmentService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController

@RequestMapping("/api/shipments")

public class ShipmentController {

    private final ShipmentService shipmentService;

    public ShipmentController(
            ShipmentService shipmentService) {

        this.shipmentService =
                shipmentService;
    }

    @PostMapping

    public ResponseEntity<ShipmentResponse>
    createShipment(

            @Valid
            @RequestBody
            ShipmentRequest request) {

        ShipmentResponse response =
                shipmentService
                        .createShipment(request);

        return new ResponseEntity<>(
                response,
                HttpStatus.CREATED);
    }

    @GetMapping

    public ResponseEntity<List<ShipmentResponse>>
    getAllShipments() {

        return ResponseEntity.ok(
                shipmentService
                        .getAllShipments());
    }

    @GetMapping("/{shipmentId}")

    public ResponseEntity<ShipmentResponse>
    getShipmentById(
            @PathVariable Integer shipmentId) {

        return ResponseEntity.ok(
                shipmentService
                        .getShipmentById(shipmentId));
    }

    @PutMapping("/{shipmentId}")

    public ResponseEntity<ShipmentResponse>
    updateShipment(

            @PathVariable Integer shipmentId,

            @Valid
            @RequestBody
            ShipmentRequest request) {

        return ResponseEntity.ok(
                shipmentService
                        .updateShipment(
                                shipmentId,
                                request));
    }

    @DeleteMapping("/{shipmentId}")

    public ResponseEntity<String>
    deleteShipment(
            @PathVariable Integer shipmentId) {

        shipmentService
                .deleteShipment(shipmentId);

        return ResponseEntity.ok(
                "Shipment deleted successfully");
    }

    @GetMapping("/customer/{customerId}")

    public ResponseEntity<List<ShipmentResponse>>
    getShipmentsByCustomerId(
            @PathVariable Integer customerId) {

        return ResponseEntity.ok(
                shipmentService
                        .getShipmentsByCustomerId(
                                customerId));
    }

    @GetMapping("/store/{storeId}")

    public ResponseEntity<List<ShipmentResponse>>
    getShipmentsByStoreId(
            @PathVariable Integer storeId) {

        return ResponseEntity.ok(
                shipmentService
                        .getShipmentsByStoreId(
                                storeId));
    }

    @GetMapping("/status/{status}")

    public ResponseEntity<List<ShipmentResponse>>
    getShipmentsByStatus(
            @PathVariable ShipmentStatus status) {

        return ResponseEntity.ok(
                shipmentService
                        .getShipmentsByStatus(
                                status));
    }
}