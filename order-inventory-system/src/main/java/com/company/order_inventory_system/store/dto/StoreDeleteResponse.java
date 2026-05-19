package com.company.order_inventory_system.store.dto;

public class StoreDeleteResponse {
    private boolean success;

    private String message;

    private StoreResponseDTO storeDeleteResponse;

    public StoreDeleteResponse(){
    }

    public StoreDeleteResponse(boolean success, String message, StoreResponseDTO storeDeleteResponse) {
        this.success = success;
        this.message = message;
        this.storeDeleteResponse = storeDeleteResponse;
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

    public StoreResponseDTO getStoreDeleteResponse() {
        return storeDeleteResponse;
    }

    public void setStoreDeleteResponse(StoreResponseDTO storeDeleteResponse) {
        this.storeDeleteResponse = storeDeleteResponse;
    }
}
