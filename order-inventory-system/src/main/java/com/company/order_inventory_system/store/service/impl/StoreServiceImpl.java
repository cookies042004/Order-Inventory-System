    package com.company.order_inventory_system.store.service.impl;

    import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
    import com.company.order_inventory_system.inventory.entity.Inventory;
    import com.company.order_inventory_system.inventory.mapper.InventoryMapper;
    import com.company.order_inventory_system.inventory.repository.InventoryRepository;
    import com.company.order_inventory_system.order.dto.OrderResponse;
    import com.company.order_inventory_system.order.entity.Order;
    import com.company.order_inventory_system.order.repository.OrderRepository;
    import com.company.order_inventory_system.order.service.OrderService;
    import com.company.order_inventory_system.order.service.OrderServiceImpl;
    import com.company.order_inventory_system.shipment.dto.ShipmentResponse;
    import com.company.order_inventory_system.shipment.entity.Shipment;
    import com.company.order_inventory_system.shipment.repository.ShipmentRepository;
    import com.company.order_inventory_system.shipment.service.ShipmentService;
    import com.company.order_inventory_system.store.dto.StoreDeleteResponse;
    import com.company.order_inventory_system.store.dto.StoreRequestDTO;
    import com.company.order_inventory_system.store.dto.StoreResponseDTO;
    import com.company.order_inventory_system.store.entity.Store;
    import com.company.order_inventory_system.common.exception.DuplicateResourceException;
    import com.company.order_inventory_system.common.exception.InvalidDataException;
    import com.company.order_inventory_system.common.exception.ResourceNotFoundException;
    import com.company.order_inventory_system.store.mapper.StoreMapper;
    import com.company.order_inventory_system.store.repository.StoreRepository;
    import com.company.order_inventory_system.store.service.StoreService;
    import org.springframework.stereotype.Service;

    import java.util.List;
    import java.util.stream.Collectors;

    @Service
    public class StoreServiceImpl implements StoreService {
        private final StoreRepository storeRepository;
        private final InventoryRepository inventoryRepository;
        private final OrderService orderService;
        private final ShipmentService shipmentService;

        public StoreServiceImpl(StoreRepository storeRepository, InventoryRepository inventoryRepository,
                                OrderService orderService, ShipmentService shipmentService){
            this.storeRepository = storeRepository;
            this.inventoryRepository = inventoryRepository;
            this.orderService = orderService;
            this.shipmentService = shipmentService;
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
        public org.springframework.data.domain.Page<StoreResponseDTO> getAllStores(org.springframework.data.domain.Pageable pageable) {
            return storeRepository.findAll(pageable)
                    .map(StoreMapper::mapToResponseDTO);
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

            // Enforce unique store name
            if (!existingStore.getStoreName().equals(storeRequestDTO.getStoreName())) {
                if (storeRepository.existsByStoreName(storeRequestDTO.getStoreName())) {
                    throw new DuplicateResourceException("Store name already exists");
                }
            }

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
        public StoreDeleteResponse deleteStore(Integer storeId){
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));

            StoreResponseDTO deletedStore = StoreMapper.mapToResponseDTO(store);

            storeRepository.delete(store);

            return new StoreDeleteResponse(
                    true,
                    "Store deleted successfully",
                    deletedStore
            );
        }

        @Override
        public List<InventoryResponseDTO> getStoreInventory(Integer storeId) {

            // validate store exists
            storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));

            List<Inventory> inventoryList = inventoryRepository.findByStoreStoreId(storeId);

            return inventoryList.stream()
                    .map(InventoryMapper::mapToResponseDTO)
                    .toList();
        }

        @Override
        public List<OrderResponse> getStoreOrders(Integer storeId) {

            // validate store exists
            storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId
                    ));

            return orderService.getOrdersByStoreId(storeId);
        }

        @Override
        public List<ShipmentResponse> getStoreShipments(Integer storeId) {

            // validate store exists
            storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId
                    ));

            return shipmentService.getShipmentsByStoreId(storeId);
        }

        @Override
        public StoreResponseDTO uploadStoreLogo(Integer storeId, byte[] logoBytes, String filename, String mimeType) {
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));
            
            store.setLogo(logoBytes);
            store.setLogoFilename(filename);
            store.setLogoMimeType(mimeType);
            store.setLogoLastUpdated(java.time.LocalDate.now());
            
            Store savedStore = storeRepository.save(store);
            return StoreMapper.mapToResponseDTO(savedStore);
        }

        @Override
        public byte[] getStoreLogo(Integer storeId) {
            Store store = storeRepository.findById(storeId)
                    .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: " + storeId));
            
            if (store.getLogo() == null) {
                throw new ResourceNotFoundException("Logo not found for store id: " + storeId);
            }
            
            return store.getLogo();
        }

        // Helper Function
        private void validateStoreAddress(StoreRequestDTO storeRequestDTO){
            // at least one address should be there either web or physical
            if((storeRequestDTO.getWebAddress() == null || storeRequestDTO.getWebAddress().isBlank())
                    && (storeRequestDTO.getPhysicalAddress() == null || storeRequestDTO.getPhysicalAddress().isBlank())){
                throw new InvalidDataException("Either web address or physical address is required");
            }
        }
    }
