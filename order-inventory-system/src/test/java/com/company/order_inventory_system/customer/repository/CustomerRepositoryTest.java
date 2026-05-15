package com.company.order_inventory_system.customer.repository;

import com.company.order_inventory_system.customer.entity.Customer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest

/* Rolls back DB changes after each test */
@Transactional

public class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    /* Test create operation */
    @Test
    void testCreateCustomer() {

        Customer customer = new Customer();

        customer.setEmailAddress("test@gmail.com");
        customer.setFullName("Test Customer");

        Customer savedCustomer =
                customerRepository.save(customer);

        assertNotNull(savedCustomer.getCustomerId());
    }

    /* Test read by ID operation */
    @Test
    void testGetCustomerById() {

        Customer customer =
                customerRepository.findById(381).orElse(null);

        assertNotNull(customer);
    }

    /* Test read all operation */
    @Test
    void testGetAllCustomers() {

        List<Customer> customers =
                customerRepository.findAll();

        assertFalse(customers.isEmpty());
    }

    /* Test update operation */
    @Test
    void testUpdateCustomer() {

        Customer customer =
                customerRepository.findById(381).orElse(null);

        assertNotNull(customer);

        customer.setFullName("Updated Name");

        Customer updatedCustomer =
                customerRepository.save(customer);

        assertEquals(
                "Updated Name",
                updatedCustomer.getFullName()
        );
    }

    /* Test delete operation */
    @Test
    void testDeleteCustomer() {

        Customer customer =
                customerRepository.findById(381).orElse(null);

        assertNotNull(customer);

        customerRepository.delete(customer);

        Customer deletedCustomer =
                customerRepository.findById(381).orElse(null);

        assertNull(deletedCustomer);
    }
}