package com.company.order_inventory_system.inventory.mapper;

import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.inventory.entity.Inventory;

public class InventoryMapper {

    private InventoryMapper() {
    }

    // Convert Entity -> ResponseDTO
    public static InventoryResponseDTO mapToResponseDTO(Inventory inventory) {

        InventoryResponseDTO responseDTO = new InventoryResponseDTO();

        responseDTO.setInventoryId(inventory.getInventoryId());

        responseDTO.setStoreId(inventory.getStore().getStoreId());

        responseDTO.setStoreName(inventory.getStore().getStoreName());

        responseDTO.setProductId(inventory.getProduct().getProductId());

        responseDTO.setProductName(inventory.getProduct().getProductName());

        responseDTO.setProductInventory(inventory.getProductInventory());

        return responseDTO;
    }
}