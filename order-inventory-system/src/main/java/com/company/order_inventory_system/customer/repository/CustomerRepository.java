package com.company.order_inventory_system.customer.repository;

import com.company.order_inventory_system.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/* Repository for Customer entity */
@Repository

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

    /* Returns customer using email address */
    Optional<Customer> findByEmailAddress(
            String emailAddress
    );

    /* Returns customers using full name */
    List<Customer> findByFullName(
            String fullName
    );
}