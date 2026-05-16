package com.company.order_inventory_system.order.service;

import com.company.order_inventory_system.order.dto.OrderRequest;
import com.company.order_inventory_system.order.dto.OrderResponse;
import com.company.order_inventory_system.order.entity.Order;
import com.company.order_inventory_system.order.enums.OrderStatus;
import com.company.order_inventory_system.order.exception.OrderNotFoundException;
import com.company.order_inventory_system.order.repository.OrderRepository;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)

class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private Order createSampleOrder() {

        Order order = new Order();

        order.setOrderId(1);
        order.setCustomerId(101);
        order.setStoreId(1);
        order.setOrderStatus(OrderStatus.OPEN);
        order.setOrderTms(LocalDateTime.now());

        return order;
    }

    private OrderRequest createOrderRequest() {

        OrderRequest request = new OrderRequest();

        request.setCustomerId(101);
        request.setStoreId(1);
        request.setOrderStatus(OrderStatus.OPEN);
        request.setOrderTms(LocalDateTime.now());

        return request;
    }

    @Test
    void testCreateOrder() {

        Order order = createSampleOrder();

        when(orderRepository.save(any(Order.class)))
                .thenReturn(order);

        OrderResponse response =
                orderService.createOrder(
                        createOrderRequest());

        assertNotNull(response);

        assertEquals(1,
                response.getOrderId());

        verify(orderRepository,
                times(1))
                .save(any(Order.class));
    }

    @Test
    void testGetAllOrders() {

        when(orderRepository.findAll())
                .thenReturn(
                        List.of(createSampleOrder()));

        List<OrderResponse> responses =
                orderService.getAllOrders();

        assertEquals(1,
                responses.size());

        verify(orderRepository,
                times(1))
                .findAll();
    }

    @Test
    void testGetOrderById() {

        when(orderRepository.findById(1))
                .thenReturn(
                        Optional.of(createSampleOrder()));

        OrderResponse response =
                orderService.getOrderById(1);

        assertNotNull(response);

        assertEquals(1,
                response.getOrderId());

        verify(orderRepository,
                times(1))
                .findById(1);
    }

    @Test
    void testGetOrderByIdNotFound() {

        when(orderRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                OrderNotFoundException.class,
                () -> orderService.getOrderById(1));

        verify(orderRepository,
                times(1))
                .findById(1);
    }

    @Test
    void testUpdateOrder() {

        Order existingOrder =
                createSampleOrder();

        when(orderRepository.findById(1))
                .thenReturn(Optional.of(existingOrder));

        when(orderRepository.save(any(Order.class)))
                .thenReturn(existingOrder);

        OrderResponse response =
                orderService.updateOrder(
                        1,
                        createOrderRequest());

        assertNotNull(response);

        assertEquals(1,
                response.getOrderId());

        verify(orderRepository,
                times(1))
                .save(any(Order.class));
    }

    @Test
    void testDeleteOrder() {

        Order order = createSampleOrder();

        when(orderRepository.findById(1))
                .thenReturn(Optional.of(order));

        doNothing().when(orderRepository)
                .delete(order);

        orderService.deleteOrder(1);

        verify(orderRepository,
                times(1))
                .delete(order);
    }

    @Test
    void testGetOrdersByCustomerId() {

        when(orderRepository.findByCustomerId(101))
                .thenReturn(
                        List.of(createSampleOrder()));

        List<OrderResponse> responses =
                orderService
                        .getOrdersByCustomerId(101);

        assertEquals(1,
                responses.size());
    }

    @Test
    void testGetOrdersByStoreId() {

        when(orderRepository.findByStoreId(1))
                .thenReturn(
                        List.of(createSampleOrder()));

        List<OrderResponse> responses =
                orderService
                        .getOrdersByStoreId(1);

        assertEquals(1,
                responses.size());
    }

    @Test
    void testGetOrdersByStatus() {

        when(orderRepository.findByOrderStatus(
                OrderStatus.OPEN))

                .thenReturn(
                        List.of(createSampleOrder()));

        List<OrderResponse> responses =
                orderService
                        .getOrdersByStatus(
                                OrderStatus.OPEN);

        assertEquals(1,
                responses.size());
    }

    @Test
    void testGetOrdersByDateRange() {

        LocalDateTime from =
                LocalDateTime.now().minusDays(1);

        LocalDateTime to =
                LocalDateTime.now();

        when(orderRepository
                .findByOrderTmsBetween(from, to))

                .thenReturn(
                        List.of(createSampleOrder()));

        List<OrderResponse> responses =
                orderService
                        .getOrdersByDateRange(from, to);

        assertEquals(1,
                responses.size());
    }
}