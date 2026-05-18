package com.company.order_inventory_system.product.shipment.repository;

import com.company.order_inventory_system.shipment.entity.Shipment;
import com.company.order_inventory_system.shipment.enums.ShipmentStatus;
import com.company.order_inventory_system.shipment.repository.ShipmentRepository;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest

@AutoConfigureTestDatabase(
        replace =
                AutoConfigureTestDatabase.Replace.NONE)

class ShipmentRepositoryTest {

    @Autowired
    private ShipmentRepository shipmentRepository;

    @Test
    void testFindByCustomerId() {

        List<Shipment> shipments =
                shipmentRepository.findByCustomerId(1);

        assertNotNull(shipments);
    }

    @Test
    void testFindByStoreId() {

        List<Shipment> shipments =
                shipmentRepository.findByStoreId(1);

        assertNotNull(shipments);
    }

    @Test
    void testFindByShipmentStatus() {

        List<Shipment> shipments =
                shipmentRepository.findByShipmentStatus(
                        ShipmentStatus.CREATED);

        assertNotNull(shipments);
    }
}