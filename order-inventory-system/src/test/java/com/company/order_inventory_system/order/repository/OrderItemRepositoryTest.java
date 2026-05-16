package com.company.order_inventory_system.order.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.company.order_inventory_system.order.entity.OrderItem;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderItemRepositoryTest {

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Test
    void testFindByOrderId() {

        List<OrderItem> items =
                orderItemRepository.findByOrderId(1);

        assertNotNull(items);
    }

    @Test
    void testFindByOrderIdAndProductId() {

        Optional<OrderItem> item =
                orderItemRepository
                        .findByOrderIdAndProductId(1, 33);

        assertTrue(item.isPresent());
    }

    @Test
    void testFindByOrderIdAndLineItemId() {

        Optional<OrderItem> item =
                orderItemRepository
                        .findByOrderIdAndLineItemId(1, 1);

        assertTrue(item.isPresent());
    }
}