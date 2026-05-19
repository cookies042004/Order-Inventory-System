package com.company.order_inventory_system.inventory.service;

import com.company.order_inventory_system.common.exception.DuplicateResourceException;
import com.company.order_inventory_system.common.exception.ResourceNotFoundException;
import com.company.order_inventory_system.inventory.dto.InventoryDeleteResponse;
import com.company.order_inventory_system.inventory.dto.InventoryRequestDTO;
import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.inventory.entity.Inventory;
import com.company.order_inventory_system.inventory.repository.InventoryRepository;
import com.company.order_inventory_system.inventory.service.impl.InventoryServiceImpl;
import com.company.order_inventory_system.product.entity.Product;
import com.company.order_inventory_system.product.repository.ProductRepository;
import com.company.order_inventory_system.store.entity.Store;
import com.company.order_inventory_system.store.repository.StoreRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class InventoryServiceImplTest {

    @Mock
    private InventoryRepository inventoryRepository;

    @Mock
    private StoreRepository storeRepository;

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private InventoryServiceImpl inventoryService;

    private Inventory inventory;

    private Store store;

    private Product product;

    private InventoryRequestDTO requestDTO;

    @BeforeEach
    void setUp() {

        store = new Store();

        store.setStoreId(1);
        store.setStoreName("Chennai Store");

        product = new Product();

        product.setProductId(1);
        product.setProductName("Laptop");
        product.setBrand("Nike");
        product.setColour("Black");
        product.setSize("M");
        product.setUnitPrice(2500.0);
        product.setRating(4);

        inventory = new Inventory();

        inventory.setInventoryId(1);
        inventory.setStore(store);
        inventory.setProduct(product);
        inventory.setProductInventory(100);

        requestDTO = new InventoryRequestDTO();

        requestDTO.setStoreId(1);
        requestDTO.setProductId(1);
        requestDTO.setProductInventory(100);
    }

    @Test
    @DisplayName("Test Create Inventory Successfully")
    void testCreateInventorySuccessfully() {

        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        when(inventoryRepository.existsByStoreStoreIdAndProductProductId(1, 1))
                .thenReturn(false);

        when(inventoryRepository.save(any(Inventory.class)))
                .thenReturn(inventory);

        InventoryResponseDTO responseDTO = inventoryService.createInventory(requestDTO);

        assertNotNull(responseDTO);

        assertEquals("Chennai Store", responseDTO.getStoreName());

        assertEquals("Laptop", responseDTO.getProductName());

        verify(inventoryRepository, times(1)).save(any(Inventory.class));
    }

    @Test
    @DisplayName("Test Duplicate Inventory")
    void testDuplicateInventory() {

        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        when(inventoryRepository.existsByStoreStoreIdAndProductProductId(1, 1))
                .thenReturn(true);

        assertThrows(DuplicateResourceException.class, () -> inventoryService.createInventory(requestDTO));

        verify(inventoryRepository, never()).save(any(Inventory.class));
    }

    @Test
    @DisplayName("Test Store Not Found")
    void testStoreNotFound() {

        when(storeRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> inventoryService.createInventory(requestDTO));
    }

    @Test
    @DisplayName("Test Product Not Found")
    void testProductNotFound() {

        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        when(productRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> inventoryService.createInventory(requestDTO));
    }

    @Test
    @DisplayName("Test Get Inventory By Id")
    void testGetInventoryById() {

        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));

        InventoryResponseDTO responseDTO = inventoryService.getInventoryById(1);

        assertNotNull(responseDTO);

        assertEquals(100, responseDTO.getProductInventory());
    }

    @Test
    @DisplayName("Test Inventory Not Found")
    void testInventoryNotFound() {

        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> inventoryService.getInventoryById(1));
    }

    @Test
    @DisplayName("Test Delete Inventory")
    void testDeleteInventory() {

        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));

        InventoryDeleteResponse responseDTO = inventoryService.deleteInventory(1);

        assertTrue(responseDTO.isSuccess());

        assertEquals("Inventory deleted successfully", responseDTO.getMessage()
        );

        verify(inventoryRepository, times(1)).delete(inventory);
    }

    @Test
    @DisplayName("Test Update Inventory Successfully")
    void testUpdateInventorySuccessfully() {

        // Mocking repository response for existing inventory
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));

        // Mocking repository response for existing store
        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        // Mocking repository response for existing product
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // Mocking save operation after updating inventory
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        // Calling service method to update inventory
        InventoryResponseDTO responseDTO = inventoryService.updateInventory(
                        1,
                        requestDTO
        );

        // Verifying response object is not null
        assertNotNull(responseDTO);

        // Checking whether inventory quantity matches expected value
        assertEquals(
                100,
                responseDTO.getProductInventory()
        );

        // Verifying save method is called exactly once
        verify(inventoryRepository, times(1))
                .save(any(Inventory.class));
    }

    @Test
    @DisplayName("Test Update Inventory Not Found")
    void testUpdateInventoryNotFound() {

        // Mocking repository response when inventory is not found
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        // Verifying that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class,
                () -> inventoryService.updateInventory(
                        1,
                        requestDTO
                )
        );
    }

    @Test
    @DisplayName("Test Get All Inventory")
    void testGetAllInventory() {

        // Mocking repository response with one inventory record
        when(inventoryRepository.findAll()).thenReturn(List.of(inventory));

        // Calling service method to fetch all inventory records
        List<InventoryResponseDTO> inventoryList = inventoryService.getAllInventory();

        // Verifying the number of inventory records returned
        assertEquals(1, inventoryList.size());

        // Verifying findAll method is called exactly once
        verify(inventoryRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Empty Inventory List")
    void testEmptyInventoryList() {

        // Mocking repository response with empty inventory list
        when(inventoryRepository.findAll()).thenReturn(List.of());

        // Calling service method to fetch all inventory records
        List<InventoryResponseDTO> inventoryList = inventoryService.getAllInventory();

        // Verifying returned inventory list is empty
        assertTrue(inventoryList.isEmpty());
    }

    @Test
    @DisplayName("Test Delete Inventory Not Found")
    void testDeleteInventoryNotFound() {

        // Mocking repository response when inventory is not found
        when(inventoryRepository.findById(1)).thenReturn(Optional.empty());

        // Verifying that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class,
                () -> inventoryService.deleteInventory(1)
        );
    }

    @Test
    @DisplayName("Test Zero Inventory Quantity")
    void testZeroInventoryQuantity() {

        // Setting inventory quantity as zero
        requestDTO.setProductInventory(0);

        // Mocking repository response for existing store
        when(storeRepository.findById(1))   .thenReturn(Optional.of(store));

        // Mocking repository response for existing product
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // Mocking repository response to check inventory uniqueness
        when(inventoryRepository.existsByStoreStoreIdAndProductProductId(1, 1))
                .thenReturn(false);

        // Mocking save operation
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        // Calling service method to create inventory
        InventoryResponseDTO responseDTO = inventoryService.createInventory(requestDTO);

        // Verifying response object is not null
        assertNotNull(responseDTO);
    }

    @Test
    @DisplayName("Test Negative Inventory Quantity")
    void testNegativeInventoryQuantity() {

        // Setting inventory quantity as negative
        requestDTO.setProductInventory(-10);

        // Verifying exception is thrown for invalid inventory quantity
        assertThrows(RuntimeException.class,
                () -> inventoryService.createInventory(requestDTO)
        );
    }

    @Test
    @DisplayName("Verify Save Method Invocation")
    void testSaveInvocation() {

        // Mocking repository response for existing store
        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        // Mocking repository response for existing product
        when(productRepository.findById(1)).thenReturn(Optional.of(product));

        // Mocking repository response to check inventory uniqueness
        when(inventoryRepository.existsByStoreStoreIdAndProductProductId(1, 1))
                .thenReturn(false);

        // Mocking save operation
        when(inventoryRepository.save(any(Inventory.class))).thenReturn(inventory);

        // Calling service method to create inventory
        inventoryService.createInventory(requestDTO);

        // Verifying save method is called exactly once
        verify(inventoryRepository,
                times(1))
                .save(any(Inventory.class));
    }

    @Test
    @DisplayName("Verify Delete Method Invocation")
    void testDeleteInvocation() {

        // Mocking repository response for existing inventory
        when(inventoryRepository.findById(1)).thenReturn(Optional.of(inventory));

        // Calling service method to delete inventory
        inventoryService.deleteInventory(1);

        // Verifying delete method is called exactly once
        verify(inventoryRepository,
                times(1))
                .delete(inventory);
    }
}