package com.company.order_inventory_system.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class InventoryRequestDTO {

    @NotNull(message = "Store ID is required")
    private Long storeId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    @NotNull(message = "Product inventory quantity is required")
    @Min(value = 0, message = "Inventory quantity cannot be negative")
    private Integer productInventory;

    public Long getStoreId() {
        return storeId;
    }

    public void setStoreId(Long storeId) {
        this.storeId = storeId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }
}