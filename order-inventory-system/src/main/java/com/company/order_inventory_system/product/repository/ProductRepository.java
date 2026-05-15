package com.company.order_inventory_system.product.repository;

import com.company.order_inventory_system.product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository // Handles database operations
public interface ProductRepository extends JpaRepository<Product, Integer> {

    // Find products by brand
    List<Product> findByBrand(String brand);

    // Find products by colour
    List<Product> findByColour(String colour);

    // Find products by size
    List<Product> findBySize(String size);
}