package com.company.order_inventory_system.store.service;

import com.company.order_inventory_system.store.dto.StoreDeleteResponse;
import com.company.order_inventory_system.store.dto.StoreRequestDTO;
import com.company.order_inventory_system.store.dto.StoreResponseDTO;
import com.company.order_inventory_system.store.entity.Store;

import com.company.order_inventory_system.common.exception.DuplicateResourceException;
import com.company.order_inventory_system.common.exception.ResourceNotFoundException;
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
import java.util.List;
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
        Integer storeId = 1;

        // Mocking store exists
        when(storeRepository.findById(storeId)).thenReturn(Optional.of(store));

        // Calling delete method
        StoreDeleteResponse responseDTO = storeService.deleteStore(1);

        // Checking response success
        assertTrue(responseDTO.isSuccess());

        // Checking response message
        assertEquals("Store deleted successfully", responseDTO.getMessage());

        // Verify delete method called once
        verify(storeRepository, times(1)).delete(store);
    }

    @Test
    @DisplayName("Test Update Store Successfully")
    void testUpdateStoreSuccessfully() {

        // Mocking repository response for existing store
        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        // Mocking save method after updating store details
        when(storeRepository.save(any(Store.class))).thenReturn(store);

        // Calling service method to update store
        StoreResponseDTO responseDTO = storeService.updateStore(1, requestDTO);

        // Verifying response object is not null
        assertNotNull(responseDTO);

        // Checking whether updated store name matches expected value
        assertEquals("Chennai Store", responseDTO.getStoreName());

        // Verifying save method is called exactly once
        verify(storeRepository, times(1)).save(any(Store.class));
    }

    @Test
    @DisplayName("Test Update Store Not Found")
    void testUpdateStoreNotFound() {

        // Mocking repository response when store is not found
        when(storeRepository.findById(1)).thenReturn(Optional.empty());

        // Verifying that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> storeService.updateStore(1, requestDTO));
    }

    @Test
    @DisplayName("Test Get All Stores")
    void testGetAllStores() {

        // Mocking repository response with one store record
        when(storeRepository.findAll()).thenReturn(List.of(store));

        // Calling service method to fetch all stores
        List<StoreResponseDTO> stores = storeService.getAllStores();

        // Verifying the number of stores returned
        assertEquals(1, stores.size());

        // Verifying findAll method is called exactly once
        verify(storeRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Test Delete Store Not Found")
    void testDeleteStoreNotFound() {

        // Mocking repository response when store does not exist
        when(storeRepository.findById(1)).thenReturn(Optional.empty());

        // Verifying that ResourceNotFoundException is thrown
        assertThrows(ResourceNotFoundException.class, () -> storeService.deleteStore(1));
    }

    @Test
    @DisplayName("Test Store Address Validation")
    void testStoreAddressValidation() {

        // Setting both web and physical address as null
        requestDTO.setWebAddress(null);
        requestDTO.setPhysicalAddress(null);

        // Mocking repository response for unique store name
        when(storeRepository.existsByStoreName(requestDTO.getStoreName())).thenReturn(false);

        // Verifying exception is thrown when both addresses are missing
        assertThrows(RuntimeException.class, () -> storeService.createStore(requestDTO));
    }

    @Test
    @DisplayName("Test Null Store Name")
    void testNullStoreName() {

        // Setting store name as null
        requestDTO.setStoreName(null);

        // Verifying exception is thrown for null store name
        assertThrows(Exception.class, () -> storeService.createStore(requestDTO));
    }

    @Test
    @DisplayName("Test Blank Store Name")
    void testBlankStoreName() {

        // Setting store name as blank
        requestDTO.setStoreName("");

        // Verifying exception is thrown for blank store name
        assertThrows(Exception.class, () -> storeService.createStore(requestDTO));
    }

    @Test
    @DisplayName("Test Create Store With Physical Address Only")
    void testPhysicalAddressOnly() {

        // Setting only physical address and keeping web address null
        requestDTO.setWebAddress(null);

        requestDTO.setPhysicalAddress("Chennai");

        // Mocking repository response for unique store name
        when(storeRepository.existsByStoreName(requestDTO.getStoreName())).thenReturn(false);

        // Mocking save operation
        when(storeRepository.save(any(Store.class))).thenReturn(store);

        // Calling service method to create store
        StoreResponseDTO responseDTO = storeService.createStore(requestDTO);

        // Verifying response object is not null
        assertNotNull(responseDTO);
    }

    @Test
    @DisplayName("Test Create Store With Web Address Only")
    void testWebAddressOnly() {

        // Setting physical address as null
        requestDTO.setPhysicalAddress(null);

        // Mocking repository response for unique store name
        when(storeRepository.existsByStoreName(requestDTO.getStoreName())).thenReturn(false);

        // Mocking save operation
        when(storeRepository.save(any(Store.class))).thenReturn(store);

        // Calling service method to create store
        StoreResponseDTO responseDTO = storeService.createStore(requestDTO);

        // Verifying response object is not null
        assertNotNull(responseDTO);
    }

    @Test
    @DisplayName("Test Empty Store List")
    void testEmptyStoreList() {

        // Mocking repository response with empty store list
        when(storeRepository.findAll()).thenReturn(List.of());

        // Calling service method to fetch all stores
        List<StoreResponseDTO> stores = storeService.getAllStores();

        // Verifying returned store list is empty
        assertTrue(stores.isEmpty());
    }

    @Test
    @DisplayName("Verify Delete Method Called Once")
    void testDeleteInvocation() {

        // Mocking repository response for existing store
        when(storeRepository.findById(1)).thenReturn(Optional.of(store));

        // Calling service method to delete store
        storeService.deleteStore(1);

        // Verifying delete method is called exactly once
        verify(storeRepository, times(1)).delete(store);
    }
}