package com.company.order_inventory_system.store.controller;

import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.order.dto.OrderResponse;
import com.company.order_inventory_system.shipment.dto.ShipmentResponse;
import com.company.order_inventory_system.store.dto.StoreDeleteResponse;
import com.company.order_inventory_system.store.dto.StoreRequestDTO;
import com.company.order_inventory_system.store.dto.StoreResponseDTO;
import com.company.order_inventory_system.store.service.StoreService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
@Validated
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // GET /api/stores
    @Operation(summary = "Get all stores")
    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> getAllStores() {

        List<StoreResponseDTO> stores = storeService.getAllStores();

        return ResponseEntity.ok(stores);
    }

    // GET /api/stores/{storeId}
    @Operation(summary = "Get store by ID")
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> getStoreById(@PathVariable
            @Positive(message = "Store ID must be positive") Integer storeId) {

        StoreResponseDTO store = storeService.getStoreById(storeId);

        return ResponseEntity.ok(store);
    }

    // POST /api/stores
    @Operation(summary = "Create store")
    @PostMapping
    public ResponseEntity<StoreResponseDTO> createStore(@Valid @RequestBody StoreRequestDTO storeRequestDTO) {

        StoreResponseDTO createdStore = storeService.createStore(storeRequestDTO);

        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }

    // PUT /api/stores/{storeId}
    @Operation(summary = "Update store")
    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> updateStore(@PathVariable
                @Positive(message = "Store ID must be positive") Integer storeId, @Valid @RequestBody StoreRequestDTO storeRequestDTO) {

        StoreResponseDTO updatedStore = storeService.updateStore(storeId, storeRequestDTO);

        return ResponseEntity.ok(updatedStore);
    }

    // DELETE /api/stores/{storeId}
    @Operation(summary = "Delete store")
    @DeleteMapping("/{storeId}")
    public ResponseEntity<StoreDeleteResponse> deleteStore(@PathVariable
                @Positive(message = "Store ID must be positive") Integer storeId) {

        StoreDeleteResponse response = storeService.deleteStore(storeId);

        return ResponseEntity.ok(response);
    }

    // GET /api/stores/{storeId}/inventory
    @Operation(summary = "Get inventory by store ID")
    @GetMapping("/{storeId}/inventory")
    public ResponseEntity<List<InventoryResponseDTO>> getStoreInventory(@PathVariable @Positive(message = "Store ID must be positive")
                      Integer storeId) {

        List<InventoryResponseDTO> inventoryList = storeService.getStoreInventory(storeId);

        return ResponseEntity.ok(inventoryList);
    }

    // GET /api/stores/{storeId}/orders
    @Operation(summary = "Get orders by store ID")
    @GetMapping("/{storeId}/orders")
    public ResponseEntity<List<OrderResponse>> getStoreOrders(@PathVariable @Positive(message = "Store ID must be positive")
            Integer storeId) {

        List<OrderResponse> orders = storeService.getStoreOrders(storeId);

        return ResponseEntity.ok(orders);
    }

    // GET /api/stores/{storeId}/shipments
    @Operation(summary = "Get shipments by store ID")
    @GetMapping("/{storeId}/shipments")
    public ResponseEntity<List<ShipmentResponse>> getStoreShipments(@PathVariable @Positive(message =
             "Store ID must be positive") Integer storeId) {

        List<ShipmentResponse> shipments = storeService.getStoreShipments(storeId);

        return ResponseEntity.ok(shipments);
    }
}