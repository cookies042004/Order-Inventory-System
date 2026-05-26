package com.company.order_inventory_system.inventory.entity;

import com.company.order_inventory_system.product.entity.Product;
import com.company.order_inventory_system.store.entity.Store;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "inventory")
public class Inventory {

    // Unique id for each inventory record
    @Id
    // Database automatically generates the id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inventory_id")
    private Integer inventoryId;

    // Many inventory records can belong to one store
    @ManyToOne
    // Foreign key column for store table
    @JoinColumn(name = "store_id")
    private Store store;

    @ManyToOne
    // Foreign key column for product table
    @JoinColumn(name = "product_id")
    private Product product;

    // Quantity of product available in the store
    @NotNull
    @Column(name = "product_inventory", nullable = false)
    private Integer productInventory;

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }

    public Inventory(Integer inventoryId, Store store, Product product, Integer productInventory) {
        this.inventoryId = inventoryId;
        this.store = store;
        this.product = product;
        this.productInventory = productInventory;
    }

    public Inventory() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Inventory)) return false;
        Inventory other = (Inventory) o;
        return this.store != null && this.store.equals(other.getStore()) &&
                this.product != null && this.product.equals(other.getProduct());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(this.store, this.product);
    }

}