package com.company.order_inventory_system.order.service;

import com.company.order_inventory_system.order.dto.OrderItemRequest;
import com.company.order_inventory_system.order.dto.OrderItemResponse;
import com.company.order_inventory_system.order.entity.OrderItem;
import com.company.order_inventory_system.order.exception.OrderItemNotFoundException;
import com.company.order_inventory_system.order.repository.OrderItemRepository;

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

@ExtendWith(MockitoExtension.class)

class OrderItemServiceTest {

    @Mock
    private OrderItemRepository orderItemRepository;

    @InjectMocks
    private OrderItemServiceImpl orderItemService;

    private OrderItem createSampleItem() {

        OrderItem item = new OrderItem();

        item.setOrderId(1);
        item.setLineItemId(1);
        item.setProductId(101);
        item.setUnitPrice(
                new BigDecimal("500.00"));
        item.setQuantity(2);
        item.setShipmentId(1);

        return item;
    }

    private OrderItemRequest createRequest() {

        OrderItemRequest request =
                new OrderItemRequest();

        request.setLineItemId(1);
        request.setProductId(101);
        request.setUnitPrice(
                new BigDecimal("500.00"));
        request.setQuantity(2);
        request.setShipmentId(1);

        return request;
    }

    @Test
    void testCreateOrderItem() {

        when(orderItemRepository.save(any(OrderItem.class)))
                .thenReturn(createSampleItem());

        OrderItemResponse response =
                orderItemService
                        .createOrderItem(createRequest());

        assertNotNull(response);

        verify(orderItemRepository,
                times(1))
                .save(any(OrderItem.class));
    }

    @Test
    void testGetAllOrderItems() {

        when(orderItemRepository.findAll())
                .thenReturn(
                        List.of(createSampleItem()));

        List<OrderItemResponse> responses =
                orderItemService.getAllOrderItems();

        assertEquals(1,
                responses.size());
    }

    @Test
    void testGetOrderItemById() {

        when(orderItemRepository.findById(1))
                .thenReturn(
                        Optional.of(createSampleItem()));

        OrderItemResponse response =
                orderItemService
                        .getOrderItemById(1);

        assertNotNull(response);
    }

    @Test
    void testGetOrderItemByIdNotFound() {

        when(orderItemRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                OrderItemNotFoundException.class,
                () -> orderItemService
                        .getOrderItemById(1));
    }

    @Test
    void testUpdateOrderItem() {

        when(orderItemRepository.findById(1))
                .thenReturn(
                        Optional.of(createSampleItem()));

        when(orderItemRepository.save(any(OrderItem.class)))
                .thenReturn(createSampleItem());

        OrderItemResponse response =
                orderItemService.updateOrderItem(
                        1,
                        createRequest());

        assertNotNull(response);
    }

    @Test
    void testDeleteOrderItem() {

        OrderItem item =
                createSampleItem();

        when(orderItemRepository.findById(1))
                .thenReturn(Optional.of(item));

        doNothing().when(orderItemRepository)
                .delete(item);

        orderItemService.deleteOrderItem(1);

        verify(orderItemRepository,
                times(1))
                .delete(item);
    }
}