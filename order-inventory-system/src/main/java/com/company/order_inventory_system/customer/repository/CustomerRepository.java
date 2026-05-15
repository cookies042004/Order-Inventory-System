package com.company.order_inventory_system.customer.repository;

import com.company.order_inventory_system.customer.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/* Repository for Customer entity */
@Repository

public interface CustomerRepository
        extends JpaRepository<Customer, Integer> {

}