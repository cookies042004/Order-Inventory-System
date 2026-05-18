package com.company.order_inventory_system.inventory.service;

import com.company.order_inventory_system.inventory.dto.InventoryRequestDTO;
import com.company.order_inventory_system.inventory.dto.InventoryResponseDTO;
import com.company.order_inventory_system.inventory.entity.Inventory;
import com.company.order_inventory_system.inventory.exception.DuplicateResourceException;
import com.company.order_inventory_system.inventory.exception.ResourceNotFoundException;
import com.company.order_inventory_system.inventory.repository.InventoryRepository;
import com.company.order_inventory_system.inventory.service.impl.InventoryServiceImpl;
import com.company.order_inventory_system.product.entity.Product;
import com.company.order_inventory_system.product.repository.ProductRepository;
import com.company.order_inventory_system.store.dto.ApiResponseDTO;
import com.company.order_inventory_system.store.entity.Store;
import com.company.order_inventory_system.store.repository.StoreRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

        ApiResponseDTO responseDTO = inventoryService.deleteInventory(1);

        assertTrue(responseDTO.isSuccess());

        assertEquals("Inventory deleted successfully", responseDTO.getMessage()
        );

        verify(inventoryRepository, times(1)).delete(inventory);
    }
}