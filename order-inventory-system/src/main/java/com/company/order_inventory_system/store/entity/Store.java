package com.company.order_inventory_system.store.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "stores")
public class Store {

    // Unique id for every store
    @Id
    // Database will automatically generate id values (AUTO_INCREMENT)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "store_id")
    private Integer storeId;

    // Name of the store
    @NotBlank
    @Column(name = "store_name", nullable = false, unique = true)
    private String storeName;

    // Website URL of the store
    @Column(name = "web_address")
    private String webAddress;

    // Physical location/address of the store
    @Column(name = "physical_address")
    private String physicalAddress;

    // Latitude used for map location
    private BigDecimal latitude;

    // Longitude used for map location
    private BigDecimal longitude;

    // Store logo image in binary format
    @Lob
    private byte[] logo;

    // Type of image like image/png or image/jpeg
    @Column(name = "logo_mime_type")
    private String logoMimeType;

    // Original uploaded file name
    @Column(name = "logo_filename")
    private String logoFilename;

    // Character encoding information if needed
    @Column(name = "logo_charset")
    private String logoCharset;

    // Stores when the logo was last updated
    @Column(name = "logo_last_updated")
    private LocalDate logoLastUpdated;

    public Integer getStoreId() {
        return storeId;
    }

    public void setStoreId(Integer storeId) {
        this.storeId = storeId;
    }

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

    public byte[] getLogo() {
        return logo;
    }

    public void setLogo(byte[] logo) {
        this.logo = logo;
    }

    public String getLogoMimeType() {
        return logoMimeType;
    }

    public void setLogoMimeType(String logoMimeType) {
        this.logoMimeType = logoMimeType;
    }

    public String getLogoFilename() {
        return logoFilename;
    }

    public void setLogoFilename(String logoFilename) {
        this.logoFilename = logoFilename;
    }

    public String getLogoCharset() {
        return logoCharset;
    }

    public void setLogoCharset(String logoCharset) {
        this.logoCharset = logoCharset;
    }

    public LocalDate getLogoLastUpdated() {
        return logoLastUpdated;
    }

    public void setLogoLastUpdated(LocalDate logoLastUpdated) {
        this.logoLastUpdated = logoLastUpdated;
    }

    public Store(Integer storeId, String storeName, String webAddress, String physicalAddress,
            BigDecimal latitude, BigDecimal longitude, byte[] logo, String logoMimeType,
            String logoFilename, String logoCharset, LocalDate logoLastUpdated) {
        this.storeId = storeId;
        this.storeName = storeName;
        this.webAddress = webAddress;
        this.physicalAddress = physicalAddress;
        this.latitude = latitude;
        this.longitude = longitude;
        this.logo = logo;
        this.logoMimeType = logoMimeType;
        this.logoFilename = logoFilename;
        this.logoCharset = logoCharset;
        this.logoLastUpdated = logoLastUpdated;
    }

    public Store() {
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Store)) return false;
        Store other = (Store) o;
        return this.storeName != null && this.storeName.equals(other.getStoreName());
    }

    @Override
    public int hashCode() {
        return java.util.Objects.hash(this.storeName);
    }
}