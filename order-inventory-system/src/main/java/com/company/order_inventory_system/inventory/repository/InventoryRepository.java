package com.company.order_inventory_system.inventory.repository;

import com.company.order_inventory_system.inventory.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, Integer> {

    // Get all inventory records for a particular store
    List<Inventory> findByStoreStoreId(Integer storeId);

    // Get inventory details for a particular product
    List<Inventory> findByProductProductId(Integer productId);

    // Checks if inventory already exists for given store and product
    boolean existsByStoreStoreIdAndProductProductId(Integer storeId, Integer productId);

}