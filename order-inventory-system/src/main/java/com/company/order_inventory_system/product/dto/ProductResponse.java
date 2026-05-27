package com.company.order_inventory_system.product.dto;

// DTO used for sending product response data
public class ProductResponse {

    private Integer productId;

    private String productName;

    private Double unitPrice;

    private String colour;

    private String brand;

    private String size;

    private Integer rating;

    // Default constructor
    public ProductResponse() {
    }

    // Parameterized constructor
    public ProductResponse(Integer productId,
                           String productName,
                           Double unitPrice,
                           String colour,
                           String brand,
                           String size,
                           Integer rating) {

        this.productId = productId;
        this.productName = productName;
        this.unitPrice = unitPrice;
        this.colour = colour;
        this.brand = brand;
        this.size = size;
        this.rating = rating;
    }

    // Getter and Setter methods

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public Double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public String getColour() {
        return colour;
    }

    public void setColour(String colour) {
        this.colour = colour;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}