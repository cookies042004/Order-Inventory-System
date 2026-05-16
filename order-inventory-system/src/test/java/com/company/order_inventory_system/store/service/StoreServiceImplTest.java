package com.company.order_inventory_system.store.service;

import com.company.order_inventory_system.common.exception.DuplicateResourceException;
import com.company.order_inventory_system.common.exception.ResourceNotFoundException;
import com.company.order_inventory_system.store.dto.ApiResponseDTO;
import com.company.order_inventory_system.store.dto.StoreRequestDTO;
import com.company.order_inventory_system.store.dto.StoreResponseDTO;
import com.company.order_inventory_system.store.entity.Store;
import com.company.order_inventory_system.store.repository.StoreRepository;
import com.company.order_inventory_system.store.service.impl.StoreServiceImpl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Enables Mockito support in JUnit 5
@ExtendWith(MockitoExtension.class)
class StoreServiceImplTest {

    // Creates mock object for repository
    @Mock
    private StoreRepository storeRepository;

    // Injects mocked repository into service class
    @InjectMocks
    private StoreServiceImpl storeService;

    // Sample Store object used in tests
    private Store store;

    // Sample request DTO used in tests
    private StoreRequestDTO requestDTO;

    @BeforeEach
    void setUp() {

        // Creating dummy store object
        store = new Store();

        store.setStoreId(1);
        store.setStoreName("Chennai Store");
        store.setWebAddress("https://store.com");
        store.setLatitude(new BigDecimal("10.123456"));
        store.setLongitude(new BigDecimal("20.654321"));

        // Creating dummy request object
        requestDTO = new StoreRequestDTO();

        requestDTO.setStoreName("Chennai Store");
        requestDTO.setWebAddress("https://store.com");
        requestDTO.setLatitude(new BigDecimal("10.123456"));
        requestDTO.setLongitude(new BigDecimal("20.654321"));
    }

    @Test
    @DisplayName("Test Create Store Successfully")
    void testCreateStoreSuccessfully() {

        // Mocking duplicate check as false
        when(storeRepository.existsByStoreName(requestDTO.getStoreName())).thenReturn(false);

        // Mocking save method
        when(storeRepository.save(any(Store.class))).thenReturn(store);

        // Calling service method
        StoreResponseDTO responseDTO = storeService.createStore(requestDTO);

        // Checking response is not null
        assertNotNull(responseDTO);

        // Verifying store name
        assertEquals("Chennai Store", responseDTO.getStoreName());

        // Verify save method called once
        verify(storeRepository, times(1)).save(any(Store.class));
    }

    @Test
    @DisplayName("Test Duplicate Store Name")
    void testDuplicateStoreName() {

        // Mocking duplicate store exists
        when(storeRepository.existsByStoreName(requestDTO.getStoreName())).thenReturn(true);

        // Checking exception is thrown
        assertThrows(DuplicateResourceException.class, () -> storeService.createStore(requestDTO));

        // Save should never be called
        verify(storeRepository, never()).save(any(Store.class));
    }

    @Test
    @DisplayName("Test Get Store By Id")
    void testGetStoreById() {

        // Mocking store found in database
        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        // Calling service method
        StoreResponseDTO responseDTO = storeService.getStoreById(1);

        // Checking response
        assertNotNull(responseDTO);

        // Verifying store name
        assertEquals("Chennai Store", responseDTO.getStoreName());
    }

    @Test
    @DisplayName("Test Store Not Found")
    void testStoreNotFound() {

        // Mocking empty result
        when(storeRepository.findById(1)).thenReturn(Optional.empty());

        // Checking exception is thrown
        assertThrows(ResourceNotFoundException.class, () -> storeService.getStoreById(1));
    }

    @Test
    @DisplayName("Test Delete Store")
    void testDeleteStore() {

        // Mocking store exists
        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        // Calling delete method
        ApiResponseDTO responseDTO = storeService.deleteStore(1);

        // Checking response success
        assertTrue(responseDTO.isSuccess());

        // Checking response message
        assertEquals("Store deleted successfully", responseDTO.getMessage());

        // Verify delete method called once
        verify(storeRepository, times(1)).delete(store);
    }
}