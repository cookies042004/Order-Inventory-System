package com.company.order_inventory_system.store.repository;

import com.company.order_inventory_system.store.entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepository extends JpaRepository<Store, Integer> {

    // Checks if a store name already exists in database
    boolean existsByStoreName(String storeName);
}