package com.company.order_inventory_system.store.service;

import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.order.dto.OrderResponse;
import com.company.order_inventory_system.shipment.dto.ShipmentResponse;
import com.company.order_inventory_system.store.dto.StoreDeleteResponse;
import com.company.order_inventory_system.store.dto.StoreRequestDTO;
import com.company.order_inventory_system.store.dto.StoreResponseDTO;

import java.util.List;

public interface StoreService {

    StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO);

    List<StoreResponseDTO> getAllStores();

    StoreResponseDTO getStoreById(Integer storeId);

    StoreResponseDTO updateStore(Integer storeId, StoreRequestDTO storeRequestDTO);
    StoreDeleteResponse deleteStore(Integer storeId);

    List<InventoryResponseDTO> getStoreInventory(Integer storeId);

    List<OrderResponse> getStoreOrders(Integer storeId);

    List<ShipmentResponse> getStoreShipments(Integer storeId);
}