package com.company.order_inventory_system.inventory.controller;

import com.company.order_inventory_system.inventory.dto.InventoryDeleteResponse;
import com.company.order_inventory_system.inventory.dto.InventoryRequestDTO;
import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.inventory.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@Validated
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // GET /api/inventory
    @Operation(summary = "Get all inventory items")
    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory() {

        List<InventoryResponseDTO> inventoryList = inventoryService.getAllInventory();

        return ResponseEntity.ok(inventoryList);
    }

    // GET /api/inventory/{inventoryId}
    @Operation(summary = "Get inventory by ID")
    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponseDTO> getInventoryById(@PathVariable
                        @Positive(message = "Inventory ID must be positive") Integer inventoryId) {

        InventoryResponseDTO inventory = inventoryService.getInventoryById(inventoryId);

        return ResponseEntity.ok(inventory);
    }

    // POST /api/inventory
    @Operation(summary = "Create inventory")
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventory(@Valid @RequestBody
                                            InventoryRequestDTO inventoryRequestDTO) {

        InventoryResponseDTO createdInventory = inventoryService.createInventory(inventoryRequestDTO);

        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }

    // PUT /api/inventory/{inventoryId}
    @Operation(summary = "Update inventory")
    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(@PathVariable
                            @Positive(message = "Inventory ID must be positive") Integer inventoryId,
                            @Valid @RequestBody InventoryRequestDTO inventoryRequestDTO) {

        InventoryResponseDTO updatedInventory = inventoryService.updateInventory(inventoryId, inventoryRequestDTO);

        return ResponseEntity.ok(updatedInventory);
    }

    // DELETE /api/inventory/{inventoryId}
    @Operation(summary = "Delete inventory")
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<InventoryDeleteResponse> deleteInventory(@PathVariable
                            @Positive(message = "Inventory must be positive") Integer inventoryId) {

        InventoryDeleteResponse response = inventoryService.deleteInventory(inventoryId);

        return ResponseEntity.ok(response);
    }

    // GET /api/inventory/store/{storeId}
    @GetMapping("/store/{storeId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoryByStoreId(@PathVariable
            @Positive(message = "Store ID must be positive") Integer storeId) {

        List<InventoryResponseDTO> inventoryList = inventoryService.getInventoryByStoreId(storeId);

        return ResponseEntity.ok(inventoryList);
    }

    // GET /api/inventory/product/{productId}
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<InventoryResponseDTO>> getInventoryByProductId(@PathVariable
                @Positive(message = "Product ID must be positive") Integer productId) {

        List<InventoryResponseDTO> inventoryList = inventoryService.getInventoryByProductId(productId);

        return ResponseEntity.ok(inventoryList);
    }
}