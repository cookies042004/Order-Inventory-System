package com.company.order_inventory_system.inventory.controller;

import com.company.order_inventory_system.inventory.dto.InventoryRequestDTO;
import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.inventory.service.InventoryService;
import com.company.order_inventory_system.store.dto.ApiResponseDTO;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    public InventoryController(InventoryService inventoryService) {
        this.inventoryService = inventoryService;
    }

    // GET /api/inventory
    @GetMapping
    public ResponseEntity<List<InventoryResponseDTO>> getAllInventory() {

        List<InventoryResponseDTO> inventoryList = inventoryService.getAllInventory();

        return ResponseEntity.ok(inventoryList);
    }

    // GET /api/inventory/{inventoryId}
    @GetMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponseDTO> getInventoryById(@PathVariable Integer inventoryId) {

        InventoryResponseDTO inventory = inventoryService.getInventoryById(inventoryId);

        return ResponseEntity.ok(inventory);
    }

    // POST /api/inventory
    @PostMapping
    public ResponseEntity<InventoryResponseDTO> createInventory(@Valid @RequestBody
                                            InventoryRequestDTO inventoryRequestDTO) {

        InventoryResponseDTO createdInventory = inventoryService.createInventory(inventoryRequestDTO);

        return new ResponseEntity<>(createdInventory, HttpStatus.CREATED);
    }

    // PUT /api/inventory/{inventoryId}
    @PutMapping("/{inventoryId}")
    public ResponseEntity<InventoryResponseDTO> updateInventory(@PathVariable Integer inventoryId,
                            @Valid @RequestBody InventoryRequestDTO inventoryRequestDTO) {

        InventoryResponseDTO updatedInventory = inventoryService.updateInventory(inventoryId, inventoryRequestDTO);

        return ResponseEntity.ok(updatedInventory);
    }

    // DELETE /api/inventory/{inventoryId}
    @DeleteMapping("/{inventoryId}")
    public ResponseEntity<ApiResponseDTO> deleteInventory(@PathVariable Integer inventoryId) {

        ApiResponseDTO response = inventoryService.deleteInventory(inventoryId);

        return ResponseEntity.ok(response);
    }
}