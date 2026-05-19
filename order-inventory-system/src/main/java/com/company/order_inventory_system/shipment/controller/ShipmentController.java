package com.company.order_inventory_system.shipment.controller;

import com.company.order_inventory_system.shipment.dto.ShipmentRequest;
import com.company.order_inventory_system.shipment.dto.ShipmentResponse;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import com.company.order_inventory_system.shipment.service.ShipmentService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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

    @Operation(summary = "Create a new shipment")
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

    @Operation(summary = "Get all shipments")

    @GetMapping
    public ResponseEntity<List<ShipmentResponse>>
    getAllShipments() {

        return ResponseEntity.ok(
                shipmentService
                        .getAllShipments());
    }

    @Operation(summary = "Get shipment by shipment ID")
    @GetMapping("/{shipmentId}")
    public ResponseEntity<ShipmentResponse>
    getShipmentById(
            @PathVariable Integer shipmentId) {

        return ResponseEntity.ok(
                shipmentService
                        .getShipmentById(shipmentId));
    }

    @Operation(summary = "Update shipment details")
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

    @Operation(summary = "Delete shipment by ID")
    @DeleteMapping("/{shipmentId}")


    public ResponseEntity<ShipmentResponse>

    deleteShipment(
            @PathVariable Integer shipmentId) {

        return ResponseEntity.ok(
                shipmentService
                        .deleteShipment(shipmentId));
    }

    @Operation(summary = "Get shipments by customer ID")
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<ShipmentResponse>>
    getShipmentsByCustomerId(
            @PathVariable Integer customerId) {

        return ResponseEntity.ok(
                shipmentService
                        .getShipmentsByCustomerId(
                                customerId));
    }

    @Operation(summary = "Get shipments by store ID")
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<ShipmentResponse>>
    getShipmentsByStoreId(
            @PathVariable Integer storeId) {

        return ResponseEntity.ok(
                shipmentService
                        .getShipmentsByStoreId(
                                storeId));
    }

    @Operation(summary = "Get shipments by shipment status")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<ShipmentResponse>>
    getShipmentsByStatus(
            @PathVariable ShipmentStatus status) {

        return ResponseEntity.ok(
                shipmentService
                        .getShipmentsByStatus(
                                status));
    }
    @PatchMapping("/{shipmentId}/status")

    public ResponseEntity<ShipmentResponse>
    updateShipmentStatus(

            @PathVariable Integer shipmentId,

            @RequestParam ShipmentStatus status) {

        return ResponseEntity.ok(
                shipmentService
                        .updateShipmentStatus(
                                shipmentId,
                                status));
    }
}