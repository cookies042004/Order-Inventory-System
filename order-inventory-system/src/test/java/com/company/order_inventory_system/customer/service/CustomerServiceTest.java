package com.company.order_inventory_system.customer.service;

import com.company.order_inventory_system.customer.dto.CustomerRequest;
import com.company.order_inventory_system.customer.dto.CustomerResponse;
import com.company.order_inventory_system.customer.entity.Customer;
import com.company.order_inventory_system.customer.exception.CustomerNotFoundException;
import com.company.order_inventory_system.customer.repository.CustomerRepository;

import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/* Enables Mockito testing support */
@ExtendWith(MockitoExtension.class)

class CustomerServiceTest {

    /* Creates mock object for repository layer */
    @Mock
    private CustomerRepository customerRepository;

    /* Injects mocked repository into service layer */
    @InjectMocks
    private CustomerServiceImpl customerService;

    /* Creates sample customer entity object */
    private Customer createSampleCustomer() {

        Customer customer = new Customer();

        customer.setCustomerId(1);

        customer.setEmailAddress(
                "customer@gmail.com"
        );

        customer.setFullName(
                "Test Customer"
        );

        return customer;
    }

    /* Creates sample customer request DTO object */
    private CustomerRequest
    createCustomerRequest() {

        CustomerRequest request =
                new CustomerRequest();

        request.setEmailAddress(
                "customer@gmail.com"
        );

        request.setFullName(
                "Test Customer"
        );

        return request;
    }

    /* Tests customer creation operation */
    @Test
    void testCreateCustomer() {

        Customer customer =
                createSampleCustomer();

        when(customerRepository.save(
                any(Customer.class)))

                .thenReturn(customer);

        CustomerResponse response =
                customerService.createCustomer(
                        createCustomerRequest()
                );

        assertNotNull(response);

        assertEquals(
                1,
                response.getCustomerId()
        );

        verify(customerRepository,
                times(1))
                .save(any(Customer.class));
    }

    /* Tests fetch customer using customer ID */
    @Test
    void testGetCustomerById() {

        when(customerRepository.findById(1))

                .thenReturn(
                        Optional.of(
                                createSampleCustomer()
                        )
                );

        CustomerResponse response =
                customerService.getCustomerById(1);

        assertNotNull(response);

        assertEquals(
                1,
                response.getCustomerId()
        );

        verify(customerRepository,
                times(1))
                .findById(1);
    }

    /* Tests customer not found exception */
    @Test
    void testGetCustomerByIdNotFound() {

        when(customerRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                CustomerNotFoundException.class,

                () -> customerService
                        .getCustomerById(1)
        );

        verify(customerRepository,
                times(1))
                .findById(1);
    }

    /* Tests fetch all customers operation */
    @Test
    void testGetAllCustomers() {

        when(customerRepository.findAll())

                .thenReturn(
                        List.of(
                                createSampleCustomer()
                        )
                );

        List<CustomerResponse> responses =
                customerService.getAllCustomers();

        assertEquals(
                1,
                responses.size()
        );

        verify(customerRepository,
                times(1))
                .findAll();
    }

    /* Tests update customer operation */
    @Test
    void testUpdateCustomer() {

        Customer existingCustomer =
                createSampleCustomer();

        Customer updatedCustomer =
                createSampleCustomer();

        updatedCustomer.setFullName(
                "Updated Customer"
        );

        updatedCustomer.setEmailAddress(
                "updated@gmail.com"
        );

        when(customerRepository.findById(1))

                .thenReturn(
                        Optional.of(existingCustomer)
                );

        when(customerRepository.save(
                any(Customer.class)))

                .thenReturn(updatedCustomer);

        CustomerResponse response =
                customerService.updateCustomer(
                        1,
                        createCustomerRequest()
                );

        assertNotNull(response);

        assertEquals(
                "Updated Customer",
                response.getFullName()
        );

        verify(customerRepository,
                times(1))
                .save(any(Customer.class));
    }

    /* Tests delete customer operation */
    @Test
    void testDeleteCustomer() {

        Customer customer =
                createSampleCustomer();

        when(customerRepository.findById(1))

                .thenReturn(
                        Optional.of(customer)
                );

        doNothing().when(customerRepository)
                .delete(customer);

        customerService.deleteCustomer(1);

        verify(customerRepository,
                times(1))
                .delete(customer);
    }
}