package com.company.order_inventory_system.store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class StoreRequestDTO {

    @NotBlank(message = "Store name is required")
    @Size(max = 255, message = "Store name cannot exceed 255 characters")
    private String storeName;

    @Size(max = 100, message = "Web address cannot exceed 100 characters")
    private String webAddress;

    @Size(max = 512, message = "Physical address cannot exceed 512 characters")
    private String physicalAddress;

    private BigDecimal latitude;

    private BigDecimal longitude;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getWebAddress() {
        return webAddress;
    }

    public void setWebAddress(String webAddress) {
        this.webAddress = webAddress;
    }

    public String getPhysicalAddress() {
        return physicalAddress;
    }

    public void setPhysicalAddress(String physicalAddress) {
        this.physicalAddress = physicalAddress;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }
}