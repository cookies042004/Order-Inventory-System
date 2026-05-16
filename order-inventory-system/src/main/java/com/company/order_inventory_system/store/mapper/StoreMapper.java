package com.company.order_inventory_system.store.mapper;

import com.company.order_inventory_system.store.dto.StoreResponseDTO;
import com.company.order_inventory_system.store.entity.Store;

public class StoreMapper {
    private StoreMapper(){

    }

    // Convert Entity -> ResponseDTO
    public static StoreResponseDTO mapToResponseDTO(Store store) {

        StoreResponseDTO responseDTO = new StoreResponseDTO();

        responseDTO.setStoreId(store.getStoreId());
        responseDTO.setStoreName(store.getStoreName());
        responseDTO.setWebAddress(store.getWebAddress());
        responseDTO.setPhysicalAddress(store.getPhysicalAddress());
        responseDTO.setLatitude(store.getLatitude());
        responseDTO.setLongitude(store.getLongitude());
        responseDTO.setLogoFilename(store.getLogoFilename());
        responseDTO.setLogoMimeType(store.getLogoMimeType());
        responseDTO.setLogoLastUpdated(store.getLogoLastUpdated());

        return responseDTO;
    }

}
