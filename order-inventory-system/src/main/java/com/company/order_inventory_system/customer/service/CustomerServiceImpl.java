package com.company.order_inventory_system.customer.service;

import com.company.order_inventory_system.customer.dto.CustomerDataRequest;
import com.company.order_inventory_system.customer.entity.Customer;
import com.company.order_inventory_system.customer.exception.CustomerNotFoundException;
import com.company.order_inventory_system.customer.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* Marks this class as service layer for customer module */
@Service

/* Implements customer business operations */
public class CustomerServiceImpl
        implements CustomerService {

    /* Used to perform database operations */
    @Autowired
    private CustomerRepository customerRepository;

    /* Used to create and store new customer details */
    @Override
    public Customer createCustomer(
            CustomerDataRequest customerDataRequest) {

        /* Creates customer entity object */
        Customer customer = new Customer();

        /* Sets customer email address */
        customer.setEmailAddress(
                customerDataRequest.getEmailAddress()
        );

        /* Sets customer full name */
        customer.setFullName(
                customerDataRequest.getFullName()
        );

        /* Saves and returns newly created customer details */
        return customerRepository.save(customer);
    }

    /* Used to fetch customer details using customer ID */
    @Override
    public Customer getCustomerById(
            Integer customerId) {

        /* Fetches customer using customer ID */
        return customerRepository.findById(customerId)

                /* Throws exception if customer with given ID does not exist */
                .orElseThrow(() ->
                        new CustomerNotFoundException(
                                "Customer details not found for the given customer ID"
                        )
                );
    }

    /* Used to fetch all available customer records */
    @Override
    public List<Customer> getAllCustomers() {

        /* Returns list of all customer records */
        return customerRepository.findAll();
    }

    /* Used to update existing customer details */
    @Override
    public Customer updateCustomer(
            Integer customerId,
            CustomerDataRequest customerDataRequest) {

        /* Fetches customer using customer ID */
        Customer customer =
                customerRepository.findById(customerId)

                        /* Throws exception if customer with given ID does not exist */
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer details not found for the given customer ID"
                                )
                        );

        /* Updates customer email address */
        customer.setEmailAddress(
                customerDataRequest.getEmailAddress()
        );

        /* Updates customer full name */
        customer.setFullName(
                customerDataRequest.getFullName()
        );

        /* Saves and returns updated customer details */
        return customerRepository.save(customer);
    }

    /* Used to delete customer details using customer ID */
    @Override
    public String deleteCustomer(
            Integer customerId) {

        /* Fetches customer using customer ID */
        Customer customer =
                customerRepository.findById(customerId)

                        /* Throws exception if customer with given ID does not exist */
                        .orElseThrow(() ->
                                new CustomerNotFoundException(
                                        "Customer details not found for the given customer ID"
                                )
                        );

        /* Deletes customer details from database */
        customerRepository.delete(customer);

        /* Returns successful deletion message */
        return "Customer details deleted successfully";
    }
}