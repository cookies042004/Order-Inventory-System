package com.company.order_inventory_system.order.repository;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import com.company.order_inventory_system.order.entity.Order;
import com.company.order_inventory_system.order.enums.OrderStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class OrderRepositoryTest {

    @Autowired
    private OrderRepository orderRepository;

    @Test
    void testFindByCustomerId() {

        List<Order> orders = orderRepository.findByCustomerId(1);

        assertNotNull(orders);
    }

    @Test
    void testFindByStoreId() {

        List<Order> orders = orderRepository.findByStoreId(1);

        assertNotNull(orders);
    }

    @Test
    void testFindByOrderStatus() {

        List<Order> orders =
                orderRepository.findByOrderStatus(OrderStatus.OPEN);

        assertNotNull(orders);
    }

    @Test
    void testFindByOrderTmsBetween() {

        LocalDateTime from =
                LocalDateTime.of(2020,1,1,0,0);

        LocalDateTime to =
                LocalDateTime.now();

        List<Order> orders =
                orderRepository.findByOrderTmsBetween(from,to);

        assertNotNull(orders);
    }
}