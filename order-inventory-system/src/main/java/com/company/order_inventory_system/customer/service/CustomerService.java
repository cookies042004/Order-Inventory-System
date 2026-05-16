package com.company.order_inventory_system.customer.service;

import com.company.order_inventory_system.customer.dto.CustomerDataRequest;
import com.company.order_inventory_system.customer.entity.Customer;

import java.util.List;

/* Provides customer-related business operations */
public interface CustomerService {

    /* Used to create and store new customer details */
    Customer createCustomer(
            CustomerDataRequest customerDataRequest
    );

    /* Used to fetch customer details using customer ID */
    Customer getCustomerById(
            Integer customerId
    );

    /* Used to fetch all available customer records */
    List<Customer> getAllCustomers();

    /* Used to modify existing customer details */
    Customer updateCustomer(
            Integer customerId,
            CustomerDataRequest customerDataRequest
    );

    /* Used to remove customer details from database */
    String deleteCustomer(
            Integer customerId
    );
}