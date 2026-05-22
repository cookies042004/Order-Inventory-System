package com.company.order_inventory_system.inventory.service;

import com.company.order_inventory_system.inventory.dto.InventoryDeleteResponse;
import com.company.order_inventory_system.inventory.dto.InventoryRequestDTO;
import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;

import java.util.List;

public interface InventoryService {

    InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO);

    List<InventoryResponseDTO> getAllInventory();

    org.springframework.data.domain.Page<InventoryResponseDTO> getAllInventory(org.springframework.data.domain.Pageable pageable);

    InventoryResponseDTO getInventoryById(Integer inventoryId);

    InventoryResponseDTO updateInventory(Integer inventoryId, InventoryRequestDTO inventoryRequestDTO);

    InventoryDeleteResponse deleteInventory(Integer inventoryId);

    List<InventoryResponseDTO> getInventoryByStoreId(Integer storeId);

    List<InventoryResponseDTO> getInventoryByProductId(Integer productId);
}