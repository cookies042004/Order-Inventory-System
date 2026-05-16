package com.company.order_inventory_system.customer.service;

import com.company.order_inventory_system.customer.dto.CustomerDataRequest;
import com.company.order_inventory_system.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/* Loads complete Spring Boot application context */
@SpringBootTest

/* Rolls back database changes after each test */
@Transactional

public class CustomerServiceTest {

    /* Injects customer service dependency */
    @Autowired
    private CustomerService customerService;

    /* Used to test customer creation operation */
    @Test
    void testCreateCustomer() {

        /* Creates DTO object */
        CustomerDataRequest customerDataRequest =
                new CustomerDataRequest();

        /* Sets customer email address */
        customerDataRequest.setEmailAddress(
                "service@gmail.com"
        );

        /* Sets customer full name */
        customerDataRequest.setFullName(
                "Service Customer"
        );

        /* Calls create customer service method */
        Customer customer =
                customerService.createCustomer(
                        customerDataRequest
                );

        /* Checks whether customer ID is generated */
        assertNotNull(customer.getCustomerId());
    }

    /* Used to test fetch customer using ID */
    @Test
    void testGetCustomerById() {

        /* Fetches customer using customer ID */
        Customer customer =
                customerService.getCustomerById(381);

        /* Checks whether customer exists */
        assertNotNull(customer);
    }

    /* Used to test fetch all customers operation */
    @Test
    void testGetAllCustomers() {

        /* Fetches all customer records */
        List<Customer> customers =
                customerService.getAllCustomers();

        /* Checks whether customer list is not empty */
        assertFalse(customers.isEmpty());
    }

    /* Used to test customer update operation */
    @Test
    void testUpdateCustomer() {

        /* Creates DTO object */
        CustomerDataRequest customerDataRequest =
                new CustomerDataRequest();

        /* Sets updated email address */
        customerDataRequest.setEmailAddress(
                "updated@gmail.com"
        );

        /* Sets updated customer name */
        customerDataRequest.setFullName(
                "Updated Customer"
        );

        /* Calls update customer service method */
        Customer updatedCustomer =
                customerService.updateCustomer(
                        381,
                        customerDataRequest
                );

        /* Checks whether customer name is updated */
        assertEquals(
                "Updated Customer",
                updatedCustomer.getFullName()
        );
    }

    /* Used to test customer delete operation */
    @Test
    void testDeleteCustomer() {

        /* Calls delete customer service method */
        String response =
                customerService.deleteCustomer(381);

        /* Checks whether customer is deleted */
        assertEquals(
                "Customer details deleted successfully",
                response
        );
    }
}