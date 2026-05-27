package com.company.order_inventory_system.product.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.util.Objects;

@Entity // Marks this class as JPA entity
@Table(name = "products") // Maps to products table
public class Product {

    @Id // Primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto increment
    @Column(name = "product_id")
    private Integer productId;

    @NotBlank(message = "Product name cannot be empty")
    @Column(name = "product_name")
    private String productName;

    @NotNull(message = "Unit price cannot be null")
    @Positive(message = "Unit price must be greater than 0")
    @Column(name = "unit_price")
    private Double unitPrice;

    @NotBlank(message = "Colour cannot be empty")
    @Column(name = "colour")
    private String colour;

    @NotBlank(message = "Brand cannot be empty")
    @Column(name = "brand")
    private String brand;

    @NotBlank(message = "Size cannot be empty")
    @Column(name = "size")
    private String size;

    @Min(value = 1, message = "Rating must be minimum 1")
    @Max(value = 5, message = "Rating must be maximum 5")
    @Column(name = "rating")
    private Integer rating;

    // Default Constructor
    public Product() {
    }

    // Parameterized Constructor
    public Product(Integer productId,
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

    // Getter and Setter Methods

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

    // Compares products using productId
    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (!(o instanceof Product)) {
            return false;
        }

        Product product = (Product) o;

        return Objects.equals(productId, product.productId);
    }

    // Generates hash value using productId
    @Override
    public int hashCode() {
        return Objects.hash(productId);
    }
}