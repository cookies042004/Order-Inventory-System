package com.company.order_inventory_system.customer.service;

import com.company.order_inventory_system.customer.dto.CustomerRequest;
import com.company.order_inventory_system.customer.dto.CustomerResponse;

import com.company.order_inventory_system.customer.entity.Customer;

import com.company.order_inventory_system.customer.exception.CustomerNotFoundException;

import com.company.order_inventory_system.customer.repository.CustomerRepository;

import org.springframework.stereotype.Service;

import java.util.List;

/* Marks this class as customer service layer */
@Service

public class CustomerServiceImpl
        implements CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerServiceImpl(
            CustomerRepository customerRepository) {

        this.customerRepository =
                customerRepository;
    }

    /* Creates new customer */
    @Override
    public CustomerResponse createCustomer(
            CustomerRequest request) {

        Customer customer =
                mapToEntity(request);

        Customer savedCustomer =
                customerRepository.save(customer);

        return mapToResponse(savedCustomer);
    }

    /* Fetches all customer records */
    @Override
    public List<CustomerResponse>
    getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    /* Fetches customer using customer ID */
    @Override
    public CustomerResponse getCustomerById(
            Integer customerId) {

        Customer customer =
                customerRepository.findById(customerId)

                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer not found with ID: "
                                                + customerId
                                ));

        return mapToResponse(customer);
    }

    /* Fetches customer using email address */
    @Override
    public CustomerResponse getCustomerByEmail(
            String emailAddress) {

        Customer customer =
                customerRepository
                        .findByEmailAddress(
                                emailAddress
                        )

                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer not found with email: "
                                                + emailAddress
                                ));

        return mapToResponse(customer);
    }

    /* Updates existing customer details */
    @Override
    public CustomerResponse updateCustomer(
            Integer customerId,
            CustomerRequest request) {

        Customer existingCustomer =
                customerRepository.findById(customerId)

                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer not found with ID: "
                                                + customerId
                                ));

        existingCustomer.setEmailAddress(
                request.getEmailAddress()
        );

        existingCustomer.setFullName(
                request.getFullName()
        );

        Customer updatedCustomer =
                customerRepository.save(
                        existingCustomer
                );

        return mapToResponse(updatedCustomer);
    }

    /* Deletes customer using customer ID */
    @Override
    public CustomerResponse deleteCustomer(
            Integer customerId) {

        Customer existingCustomer =
                customerRepository.findById(customerId)

                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer not found with ID: "
                                                + customerId
                                ));

        CustomerResponse deletedCustomer =
                mapToResponse(existingCustomer);

        customerRepository.delete(existingCustomer);

        return deletedCustomer;
    }

    /* Converts request DTO into entity */
    private Customer mapToEntity(
            CustomerRequest request) {

        Customer customer = new Customer();

        customer.setEmailAddress(
                request.getEmailAddress()
        );

        customer.setFullName(
                request.getFullName()
        );

        return customer;
    }

    /* Converts entity into response DTO */
    private CustomerResponse mapToResponse(
            Customer customer) {

        CustomerResponse response =
                new CustomerResponse();

        response.setCustomerId(
                customer.getCustomerId()
        );

        response.setEmailAddress(
                customer.getEmailAddress()
        );

        response.setFullName(
                customer.getFullName()
        );

        return response;
    }
}