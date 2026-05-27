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

@ExtendWith(MockitoExtension.class)

class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;


    /* =========================
       SAMPLE TEST DATA
       ========================= */

    private Customer createSampleCustomer() {

        Customer customer = new Customer();

        customer.setCustomerId(1);

        customer.setFullName(
                "Test Customer"
        );

        customer.setEmailAddress(
                "customer@gmail.com"
        );

        return customer;
    }

    private CustomerRequest createCustomerRequest() {

        CustomerRequest request =
                new CustomerRequest();

        request.setFullName(
                "Test Customer"
        );

        request.setEmailAddress(
                "customer@gmail.com"
        );

        return request;
    }


    /* =========================
       CREATE CUSTOMER TEST
       ========================= */

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


    /* =========================
       GET CUSTOMER BY ID
       ========================= */

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


    /* =========================
       CUSTOMER NOT FOUND
       ========================= */

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


    /* =========================
       GET ALL CUSTOMERS
       ========================= */

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


    /* =========================
       EMPTY CUSTOMER LIST
       ========================= */

    @Test
    void testGetAllCustomersEmptyList() {

        when(customerRepository.findAll())
                .thenReturn(List.of());

        List<CustomerResponse> responses =
                customerService.getAllCustomers();

        assertTrue(
                responses.isEmpty()
        );

        verify(customerRepository,
                times(1))
                .findAll();
    }


    /* =========================
       UPDATE CUSTOMER
       ========================= */

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


    /* =========================
       UPDATE CUSTOMER NOT FOUND
       ========================= */

    @Test
    void testUpdateCustomerNotFound() {

        when(customerRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                CustomerNotFoundException.class,

                () -> customerService.updateCustomer(
                        1,
                        createCustomerRequest()
                )
        );

        verify(customerRepository,
                times(1))
                .findById(1);
    }


    /* =========================
       VERIFY UPDATE SAVE CALL
       ========================= */

    @Test
    void testUpdateCustomerRepositorySaveCalled() {

        Customer existingCustomer =
                createSampleCustomer();

        when(customerRepository.findById(1))
                .thenReturn(
                        Optional.of(existingCustomer)
                );

        when(customerRepository.save(
                any(Customer.class)))
                .thenReturn(existingCustomer);

        customerService.updateCustomer(
                1,
                createCustomerRequest()
        );

        verify(customerRepository,
                times(1))
                .save(any(Customer.class));
    }


    /* =========================
       DELETE CUSTOMER
       ========================= */

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


    /* =========================
       DELETE CUSTOMER NOT FOUND
       ========================= */

    @Test
    void testDeleteCustomerNotFound() {

        when(customerRepository.findById(1))
                .thenReturn(Optional.empty());

        assertThrows(
                CustomerNotFoundException.class,

                () -> customerService.deleteCustomer(1)
        );

        verify(customerRepository,
                times(1))
                .findById(1);
    }


    /* =========================
       VERIFY DELETE CALL
       ========================= */

    @Test
    void testDeleteCustomerRepositoryDeleteCalled() {

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


    /* =========================
       VERIFY CREATE SAVE CALL
       ========================= */

    @Test
    void testCreateCustomerRepositorySaveCalled() {

        Customer customer =
                createSampleCustomer();

        when(customerRepository.save(
                any(Customer.class)))
                .thenReturn(customer);

        customerService.createCustomer(
                createCustomerRequest()
        );

        verify(customerRepository,
                times(1))
                .save(any(Customer.class));
    }


    /* =========================
       VERIFY FIND ALL CALL
       ========================= */

    @Test
    void testGetAllCustomersRepositoryFindAllCalled() {

        when(customerRepository.findAll())
                .thenReturn(
                        List.of(
                                createSampleCustomer()
                        )
                );

        customerService.getAllCustomers();

        verify(customerRepository,
                times(1))
                .findAll();
    }
}