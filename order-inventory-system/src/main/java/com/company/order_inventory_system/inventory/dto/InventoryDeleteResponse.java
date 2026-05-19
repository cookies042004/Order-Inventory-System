package com.company.order_inventory_system.inventory.dto;

public class InventoryDeleteResponse {
    private boolean success;

    private String message;

    private InventoryResponseDTO inventoryDeleteResponse;

    public InventoryDeleteResponse() {
    }

    public InventoryDeleteResponse(boolean success, String message, InventoryResponseDTO inventoryDeleteResponse) {
        this.success = success;
        this.message = message;
        this.inventoryDeleteResponse = inventoryDeleteResponse;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public InventoryResponseDTO getInventoryDeleteResponse() {
        return inventoryDeleteResponse;
    }

    public void setInventoryDeleteResponse(InventoryResponseDTO inventoryDeleteResponse) {
        this.inventoryDeleteResponse = inventoryDeleteResponse;
    }
}
