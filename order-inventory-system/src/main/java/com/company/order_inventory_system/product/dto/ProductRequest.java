package com.company.order_inventory_system.product.dto;

import jakarta.validation.constraints.*;

// DTO used for product request data
public class ProductRequest {

    @NotBlank(message = "Product name cannot be empty")
    private String productName;

    @NotNull(message = "Unit price cannot be null")
    @Positive(message = "Unit price must be greater than 0")
    private Double unitPrice;

    @NotBlank(message = "Colour cannot be empty")
    private String colour;

    @NotBlank(message = "Brand cannot be empty")
    private String brand;

    @NotBlank(message = "Size cannot be empty")
    private String size;

    @Min(value = 1, message = "Rating must be minimum 1")
    @Max(value = 5, message = "Rating must be maximum 5")
    private Integer rating;

    // Default Constructor
    public ProductRequest() {
    }

    // Parameterized Constructor
    public ProductRequest(String productName,
                          Double unitPrice,
                          String colour,
                          String brand,
                          String size,
                          Integer rating) {

        this.productName = productName;
        this.unitPrice = unitPrice;
        this.colour = colour;
        this.brand = brand;
        this.size = size;
        this.rating = rating;
    }

    // Getter and Setter Methods

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