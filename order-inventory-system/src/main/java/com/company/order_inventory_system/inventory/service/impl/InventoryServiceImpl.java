package com.company.order_inventory_system.inventory.service.impl;

import com.company.order_inventory_system.common.exception.DuplicateResourceException;
import com.company.order_inventory_system.common.exception.InvalidDataException;
import com.company.order_inventory_system.common.exception.ResourceNotFoundException;
import com.company.order_inventory_system.inventory.dto.InventoryDeleteResponse;
import com.company.order_inventory_system.store.dto.ApiResponseDTO;
import com.company.order_inventory_system.inventory.dto.InventoryRequestDTO;
import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.inventory.entity.Inventory;
import com.company.order_inventory_system.inventory.mapper.InventoryMapper;
import com.company.order_inventory_system.inventory.repository.InventoryRepository;
import com.company.order_inventory_system.inventory.service.InventoryService;
import com.company.order_inventory_system.product.entity.Product;
import com.company.order_inventory_system.product.repository.ProductRepository;
import com.company.order_inventory_system.store.entity.Store;
import com.company.order_inventory_system.store.repository.StoreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;

    private final StoreRepository storeRepository;

    private final ProductRepository productRepository;

    public InventoryServiceImpl(InventoryRepository inventoryRepository,
                                StoreRepository storeRepository,
                                ProductRepository productRepository) {
        this.inventoryRepository = inventoryRepository;
        this.storeRepository = storeRepository;
        this.productRepository = productRepository;
    }

    @Override
    public InventoryResponseDTO createInventory(InventoryRequestDTO inventoryRequestDTO) {

        // Validate store existence
        Store store = storeRepository.findById(inventoryRequestDTO.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: "
                        + inventoryRequestDTO.getStoreId()));

        // Validate product existence
        Product product = productRepository.findById(inventoryRequestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "
                                        + inventoryRequestDTO.getProductId()));

        // Validate duplicate inventory
        boolean exists = inventoryRepository.existsByStoreStoreIdAndProductProductId(
                                inventoryRequestDTO.getStoreId(),
                                inventoryRequestDTO.getProductId()
        );

        if (exists) {
            throw new DuplicateResourceException("Inventory already exists for this store and product");
        }

        validateInventoryQuantity(inventoryRequestDTO.getProductInventory());

        // Create inventory entity
        Inventory inventory = new Inventory();

        inventory.setStore(store);

        inventory.setProduct(product);

        inventory.setProductInventory(inventoryRequestDTO.getProductInventory());

        // Save inventory
        Inventory savedInventory = inventoryRepository.save(inventory);

        // Convert Entity -> ResponseDTO
        return InventoryMapper
                .mapToResponseDTO(savedInventory);
    }

    @Override
    public List<InventoryResponseDTO>
    getAllInventory() {

        List<Inventory> inventoryList = inventoryRepository.findAll();

        return inventoryList.stream()
                .map(InventoryMapper::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public org.springframework.data.domain.Page<InventoryResponseDTO> getAllInventory(org.springframework.data.domain.Pageable pageable) {
        return inventoryRepository.findAll(pageable)
                .map(InventoryMapper::mapToResponseDTO);
    }

    @Override
    public InventoryResponseDTO getInventoryById(Integer inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: "
                                                + inventoryId));

        return InventoryMapper.mapToResponseDTO(inventory);
    }

    @Override
    public InventoryResponseDTO updateInventory(Integer inventoryId, InventoryRequestDTO inventoryRequestDTO) {

        Inventory existingInventory = inventoryRepository.findById(inventoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: "
                                                + inventoryId));

        // Validate store existence
        Store store = storeRepository.findById(inventoryRequestDTO.getStoreId())
                .orElseThrow(() -> new ResourceNotFoundException("Store not found with id: "
                                        + inventoryRequestDTO.getStoreId()));

        // Validate product existence
        Product product = productRepository.findById(inventoryRequestDTO.getProductId())
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: "
                                        + inventoryRequestDTO.getProductId()));

        validateInventoryQuantity(inventoryRequestDTO.getProductInventory());

        existingInventory.setStore(store);

        existingInventory.setProduct(product);

        existingInventory.setProductInventory(inventoryRequestDTO.getProductInventory());

        Inventory updatedInventory = inventoryRepository.save(existingInventory);

        return InventoryMapper.mapToResponseDTO(updatedInventory);
    }

    @Override
    public InventoryDeleteResponse deleteInventory(
            Integer inventoryId) {

        Inventory inventory = inventoryRepository.findById(inventoryId)
                        .orElseThrow(() -> new ResourceNotFoundException("Inventory not found with id: "
                                                + inventoryId));

        InventoryResponseDTO deletedInventory = InventoryMapper.mapToResponseDTO(inventory);

        inventoryRepository.delete(inventory);

        return new InventoryDeleteResponse(
                true,
                "Inventory deleted successfully",
                deletedInventory
        );
    }

    @Override
    public List<InventoryResponseDTO> getInventoryByStoreId(Integer storeId) {

        List<Inventory> inventoryList = inventoryRepository.findByStoreStoreId(storeId);

        return inventoryList.stream()
                .map(InventoryMapper::mapToResponseDTO)
                .toList();
    }

    @Override
    public List<InventoryResponseDTO> getInventoryByProductId(Integer productId) {

        List<Inventory> inventoryList = inventoryRepository.findByProductProductId(productId);

        return inventoryList.stream()
                .map(InventoryMapper::mapToResponseDTO)
                .toList();
    }

    // Helper Function
    private void validateInventoryQuantity(Integer quantity) {
        if (quantity < 0) {
            throw new InvalidDataException("Inventory quantity cannot be negative");
        }
    }
}