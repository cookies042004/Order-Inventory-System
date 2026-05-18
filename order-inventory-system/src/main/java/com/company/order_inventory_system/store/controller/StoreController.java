package com.company.order_inventory_system.store.controller;

import com.company.order_inventory_system.store.dto.ApiResponseDTO;
import com.company.order_inventory_system.store.dto.StoreRequestDTO;
import com.company.order_inventory_system.store.dto.StoreResponseDTO;
import com.company.order_inventory_system.store.service.StoreService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/stores")
public class StoreController {

    private final StoreService storeService;

    public StoreController(StoreService storeService) {
        this.storeService = storeService;
    }

    // GET /api/stores
    @GetMapping
    public ResponseEntity<List<StoreResponseDTO>> getAllStores() {

        List<StoreResponseDTO> stores = storeService.getAllStores();

        return ResponseEntity.ok(stores);
    }

    // GET /api/stores/{storeId}
    @GetMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> getStoreById(@PathVariable Integer storeId) {

        StoreResponseDTO store = storeService.getStoreById(storeId);

        return ResponseEntity.ok(store);
    }

    // POST /api/stores
    @PostMapping
    public ResponseEntity<StoreResponseDTO> createStore(@Valid @RequestBody StoreRequestDTO storeRequestDTO) {

        StoreResponseDTO createdStore = storeService.createStore(storeRequestDTO);

        return new ResponseEntity<>(createdStore, HttpStatus.CREATED);
    }

    // PUT /api/stores/{storeId}
    @PutMapping("/{storeId}")
    public ResponseEntity<StoreResponseDTO> updateStore(@PathVariable Integer storeId, @Valid @RequestBody StoreRequestDTO storeRequestDTO) {

        StoreResponseDTO updatedStore = storeService.updateStore(storeId, storeRequestDTO);

        return ResponseEntity.ok(updatedStore);
    }

    // DELETE /api/stores/{storeId}
    @DeleteMapping("/{storeId}")
    public ResponseEntity<ApiResponseDTO> deleteStore(@PathVariable Integer storeId) {

        ApiResponseDTO response = storeService.deleteStore(storeId);

        return ResponseEntity.ok(response);
    }
}