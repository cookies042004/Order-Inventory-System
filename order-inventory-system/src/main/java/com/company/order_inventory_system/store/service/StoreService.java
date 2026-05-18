package com.company.order_inventory_system.store.service;

import com.company.order_inventory_system.store.dto.ApiResponseDTO;
import com.company.order_inventory_system.store.dto.StoreRequestDTO;
import com.company.order_inventory_system.store.dto.StoreResponseDTO;

import java.util.List;

public interface StoreService {

    StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO);

    List<StoreResponseDTO> getAllStores();

    StoreResponseDTO getStoreById(Integer storeId);

    StoreResponseDTO updateStore(Integer storeId, StoreRequestDTO storeRequestDTO);
    ApiResponseDTO deleteStore(Integer storeId);
}