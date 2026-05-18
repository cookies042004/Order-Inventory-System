package com.company.order_inventory_system.inventory.service;

import com.company.order_inventory_system.inventory.dto.InventoryRequestDTO;
import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.store.dto.ApiResponseDTO;

import java.util.List;

public interface InventoryService {

    InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO);

    List<InventoryResponseDTO> getAllInventory();

    InventoryResponseDTO getInventoryById(Integer inventoryId);

    InventoryResponseDTO updateInventory(Integer inventoryId, InventoryRequestDTO inventoryRequestDTO);

    ApiResponseDTO deleteInventory(Integer inventoryId);
}