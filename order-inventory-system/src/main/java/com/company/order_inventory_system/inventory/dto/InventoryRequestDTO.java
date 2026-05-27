package com.company.order_inventory_system.inventory.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public class InventoryRequestDTO {

    @NotNull(message = "Store ID is required")
    private Integer storeId;

    @NotNull(message = "Product ID is required")
    private Integer productId;

    @NotNull(message = "Product inventory quantity is required")
    @Min(value = 0, message = "Inventory quantity cannot be negative")
    private Integer productInventory;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Integer getProductInventory() {
        return productInventory;
    }

    public void setProductInventory(Integer productInventory) {
        this.productInventory = productInventory;
    }
}