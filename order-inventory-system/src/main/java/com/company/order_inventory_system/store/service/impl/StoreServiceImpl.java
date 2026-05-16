    package com.company.order_inventory_system.store.service.impl;

    import com.company.order_inventory_system.store.dto.ApiResponseDTO;
    import com.company.order_inventory_system.store.dto.StoreRequestDTO;
    import com.company.order_inventory_system.store.dto.StoreResponseDTO;
    import com.company.order_inventory_system.store.entity.Store;
    import com.company.order_inventory_system.store.exception.DuplicateResourceException;
    import com.company.order_inventory_system.store.exception.InvalidDataException;
    import com.company.order_inventory_system.store.exception.ResourceNotFoundException;
    import com.company.order_inventory_system.store.mapper.StoreMapper;
    import com.company.order_inventory_system.store.repository.StoreRepository;
    import com.company.order_inventory_system.store.service.StoreService;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class StoreServiceImpl implements StoreService {
        private StoreRepository storeRepository;

        public StoreServiceImpl(StoreRepository storeRepository){
            this.storeRepository = storeRepository;
        }

        @Override
        public StoreResponseDTO createStore(StoreRequestDTO storeRequestDTO){
            // check duplicate store name
            if(storeRepository.existsByStoreName(storeRequestDTO.getStoreName())){
                throw new DuplicateResourceException("Store name already exists");
            }

            validateStoreAddress(storeRequestDTO);

            Store store = new Store();
            store.setStoreName(storeRequestDTO.getStoreName());
            store.setWebAddress(storeRequestDTO.getWebAddress());
            store.setPhysicalAddress(storeRequestDTO.getPhysicalAddress());
            store.setLongitude(storeRequestDTO.getLongitude());
            store.setLatitude(storeRequestDTO.getLatitude());

            // save entity
            Store savedStore = storeRepository.save(store);

            // converting entity-> ResponseDTO
            return StoreMapper.mapToResponseDTO(savedStore);
        }

        @Override
        public List<StoreResponseDTO> getAllStores(){
            List<Store> stores = storeRepository.findAll();

            return stores.stream()
                    .map(StoreMapper::mapToResponseDTO)
                    .collect(Collectors.toList());
        }

        @Override
        public StoreResponseDTO getStoreById(Integer storeId){
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));

            return StoreMapper.mapToResponseDTO(store);
        }

        @Override
        public StoreResponseDTO updateStore(Integer storeId, StoreRequestDTO storeRequestDTO){
            Store existingStore = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));

            validateStoreAddress(storeRequestDTO);

            // update existing entity fields
            existingStore.setStoreName(storeRequestDTO.getStoreName());

            existingStore.setWebAddress(storeRequestDTO.getWebAddress());

            existingStore.setPhysicalAddress(storeRequestDTO.getPhysicalAddress());

            existingStore.setLatitude(storeRequestDTO.getLatitude());

            existingStore.setLongitude(storeRequestDTO.getLongitude());

            // save updated entity
            Store updatedStore = storeRepository.save(existingStore);

            // convert Entity -> ResponseDTO
            return StoreMapper.mapToResponseDTO(updatedStore);
        }

        @Override
        public ApiResponseDTO deleteStore(Integer storeId){
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));

            storeRepository.delete(store);

            return new ApiResponseDTO(
                    true,
                    "Store deleted successfully"
            );
        }

        private void validateStoreAddress(StoreRequestDTO storeRequestDTO){
            // at least one address should be there either web or physical
            if((storeRequestDTO.getWebAddress() == null || storeRequestDTO.getWebAddress().isBlank())
                    && (storeRequestDTO.getPhysicalAddress() == null || storeRequestDTO.getPhysicalAddress().isBlank())){
                throw new InvalidDataException("Either web address or physical address is required");
            }
        }
    }
