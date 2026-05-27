package com.company.order_inventory_system.shipment.service;

import com.company.order_inventory_system.customer.repository.CustomerRepository;
import com.company.order_inventory_system.shipment.dto.ShipmentRequest;
import com.company.order_inventory_system.shipment.dto.ShipmentResponse;
import com.company.order_inventory_system.shipment.entity.Shipment;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import com.company.order_inventory_system.shipment.exception.ShipmentNotFoundException;
import com.company.order_inventory_system.shipment.repository.ShipmentRepository;
import com.company.order_inventory_system.store.repository.StoreRepository;
import com.company.order_inventory_system.customer.exception.CustomerNotFoundException;
import com.company.order_inventory_system.common.exception.ResourceNotFoundException;

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

class ShipmentServiceTest {

    @Mock
    private ShipmentRepository shipmentRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private StoreRepository storeRepository;

    @InjectMocks
    private ShipmentServiceImpl shipmentService;

    private Shipment createSampleShipment() {

        Shipment shipment = new Shipment();

        shipment.setShipmentId(1);
        shipment.setStoreId(1);
        shipment.setCustomerId(101);
        shipment.setDeliveryAddress(
                "Chennai, Tamil Nadu");

        shipment.setShipmentStatus(
                ShipmentStatus.CREATED);

        return shipment;
    }

    private ShipmentRequest createRequest() {

        ShipmentRequest request =
                new ShipmentRequest();

        request.setStoreId(1);
        request.setCustomerId(101);
        request.setDeliveryAddress(
                "Chennai, Tamil Nadu");

        request.setShipmentStatus(
                ShipmentStatus.CREATED);

        return request;
    }

    @Test
    void testCreateShipment() {

        when(customerRepository.existsById(101)).thenReturn(true);
        when(storeRepository.existsById(1)).thenReturn(true);
        when(shipmentRepository.save(any(Shipment.class)))
                .thenReturn(createSampleShipment());

        ShipmentResponse response =
                shipmentService.createShipment(
                        createRequest());

        assertNotNull(response);

        verify(shipmentRepository,
                times(1))
                .save(any(Shipment.class));
    }

    @Test
    void testGetAllShipments() {

        when(shipmentRepository.findAll())
                .thenReturn(
                        List.of(createSampleShipment()));

        List<ShipmentResponse> responses =
                shipmentService.getAllShipments();

        assertEquals(1,
                responses.size());

        verify(shipmentRepository,
                times(1))
                .findAll();
    }

    @Test
    void testGetShipmentById() {

        when(shipmentRepository.findById(1))
                .thenReturn(
                        Optional.of(createSampleShipment()));

        ShipmentResponse response =
                shipmentService.getShipmentById(1);

        assertNotNull(response);

        assertEquals(1,
                response.getShipmentId());

        verify(shipmentRepository,
                times(1))
                .findById(1);
    }

    @Test
    void testGetShipmentByIdNotFound() {

        when(shipmentRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                ShipmentNotFoundException.class,
                () -> shipmentService
                        .getShipmentById(1));

        verify(shipmentRepository,
                times(1))
                .findById(1);
    }

    @Test
    void testUpdateShipment() {

        Shipment shipment =
                createSampleShipment();

        when(shipmentRepository.findById(1))
                .thenReturn(Optional.of(shipment));

        when(customerRepository.existsById(101)).thenReturn(true);
        when(storeRepository.existsById(1)).thenReturn(true);
        when(shipmentRepository.save(any(Shipment.class)))
                .thenReturn(shipment);

        ShipmentResponse response =
                shipmentService.updateShipment(
                        1,
                        createRequest());

        assertNotNull(response);

        assertEquals(1,
                response.getShipmentId());

        verify(shipmentRepository,
                times(1))
                .save(any(Shipment.class));
    }

    @Test
    void testDeleteShipment() {

        Shipment shipment =
                createSampleShipment();

        when(shipmentRepository.findById(1))
                .thenReturn(Optional.of(shipment));

        doNothing().when(shipmentRepository)
                .delete(shipment);

        shipmentService.deleteShipment(1);

        verify(shipmentRepository,
                times(1))
                .delete(shipment);
    }

    @Test
    void testGetShipmentsByCustomerId() {

        when(customerRepository.existsById(101)).thenReturn(true);
        when(shipmentRepository
                .findByCustomerId(101))

                .thenReturn(
                        List.of(createSampleShipment()));

        List<ShipmentResponse> responses =
                shipmentService
                        .getShipmentsByCustomerId(101);

        assertEquals(1,
                responses.size());
    }

    @Test
    void testGetShipmentsByStoreId() {

        when(storeRepository.existsById(1)).thenReturn(true);
        when(shipmentRepository
                .findByStoreId(1))

                .thenReturn(
                        List.of(createSampleShipment()));

        List<ShipmentResponse> responses =
                shipmentService
                        .getShipmentsByStoreId(1);

        assertEquals(1,
                responses.size());
    }

    @Test
    void testGetShipmentsByStatus() {

        when(shipmentRepository
                .findByShipmentStatus(
                        ShipmentStatus.CREATED))

                .thenReturn(
                        List.of(createSampleShipment()));

        List<ShipmentResponse> responses =
                shipmentService
                        .getShipmentsByStatus(
                                ShipmentStatus.CREATED);

        assertEquals(1,
                responses.size());
    }

    @Test
    void testGetShipmentsByCustomerIdCustomerNotFound() {
        when(customerRepository.existsById(101)).thenReturn(false);
        assertThrows(CustomerNotFoundException.class, () -> shipmentService.getShipmentsByCustomerId(101));
    }

    @Test
    void testGetShipmentsByStoreIdStoreNotFound() {
        when(storeRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> shipmentService.getShipmentsByStoreId(1));
    }

    @Test
    void testCreateShipmentCustomerNotFound() {
        ShipmentRequest request = createRequest();
        when(customerRepository.existsById(101)).thenReturn(false);
        assertThrows(CustomerNotFoundException.class, () -> shipmentService.createShipment(request));
    }

    @Test
    void testCreateShipmentStoreNotFound() {
        ShipmentRequest request = createRequest();
        when(customerRepository.existsById(101)).thenReturn(true);
        when(storeRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> shipmentService.createShipment(request));
    }

    @Test
    void testUpdateShipmentCustomerNotFound() {
        ShipmentRequest request = createRequest();
        when(shipmentRepository.findById(1)).thenReturn(Optional.of(createSampleShipment()));
        when(customerRepository.existsById(101)).thenReturn(false);
        assertThrows(CustomerNotFoundException.class, () -> shipmentService.updateShipment(1, request));
    }

    @Test
    void testUpdateShipmentStoreNotFound() {
        ShipmentRequest request = createRequest();
        when(shipmentRepository.findById(1)).thenReturn(Optional.of(createSampleShipment()));
        when(customerRepository.existsById(101)).thenReturn(true);
        when(storeRepository.existsById(1)).thenReturn(false);
        assertThrows(ResourceNotFoundException.class, () -> shipmentService.updateShipment(1, request));
    }
}