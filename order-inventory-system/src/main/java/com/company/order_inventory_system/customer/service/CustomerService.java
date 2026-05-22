package com.company.order_inventory_system.customer.service;

import com.company.order_inventory_system.customer.dto.CustomerRequest;
import com.company.order_inventory_system.customer.dto.CustomerResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/* Provides customer business operations */
public interface CustomerService {

    /* Creates new customer */
    CustomerResponse createCustomer(
            CustomerRequest request
    );

    /* Fetches all customer records */
    List<CustomerResponse> getAllCustomers();

    /* Fetches all customer records (paginated) */
    Page<CustomerResponse> getAllCustomers(Pageable pageable);

    /* Fetches customer using customer ID */
    CustomerResponse getCustomerById(
            Integer customerId
    );

    /* Fetches customer using email address */
    CustomerResponse getCustomerByEmail(
            String emailAddress
    );

    /* Updates existing customer details */
    CustomerResponse updateCustomer(
            Integer customerId,
            CustomerRequest request
    );

    /* Deletes customer using customer ID */
    CustomerResponse deleteCustomer(
            Integer customerId
    );
}