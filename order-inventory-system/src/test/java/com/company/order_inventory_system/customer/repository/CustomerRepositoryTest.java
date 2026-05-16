package com.company.order_inventory_system.customer.repository;

import com.company.order_inventory_system.customer.entity.Customer;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

/* Used for repository layer testing */
@DataJpaTest

/* Prevents replacing actual database with in-memory database */
@AutoConfigureTestDatabase(
        replace =
                AutoConfigureTestDatabase.Replace.NONE
)

class CustomerRepositoryTest {

    /* Injects customer repository dependency */
    @Autowired
    private CustomerRepository customerRepository;

    /* Tests fetch customer using email address */
    @Test
    void testFindByEmailAddress() {

        Optional<Customer> customer =
                customerRepository.findByEmailAddress(
                        "sample@gmail.com"
                );

        assertNotNull(customer);
    }

    /* Tests fetch customers using full name */
    @Test
    void testFindByFullName() {

        List<Customer> customers =
                customerRepository.findByFullName(
                        "Customer"
                );

        assertNotNull(customers);
    }

    /* Tests fetch customer using customer ID */
    @Test
    void testFindById() {

        Optional<Customer> customer =
                customerRepository.findById(1);

        assertNotNull(customer);
    }

    /* Tests fetch all customer records */
    @Test
    void testFindAllCustomers() {

        List<Customer> customers =
                customerRepository.findAll();

        assertNotNull(customers);
    }
}