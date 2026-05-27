package com.company.order_inventory_system.report.dto;

import java.math.BigDecimal;

// DTO for Sales Summary Report response
public class SalesSummaryResponse {

    // Stores total revenue generated
    private BigDecimal totalRevenue;

    // Stores total completed orders count
    private Long totalOrders;

    // Stores average order value
    private BigDecimal averageOrderValue;

    // Parameterized constructor
    public SalesSummaryResponse(
            BigDecimal totalRevenue,
            Long totalOrders,
            BigDecimal averageOrderValue
    ) {
        this.totalRevenue = totalRevenue;
        this.totalOrders = totalOrders;
        this.averageOrderValue = averageOrderValue;
    }

    // Getter for total revenue
    public BigDecimal getTotalRevenue() {
        return totalRevenue;
    }

    // Setter for total revenue
    public void setTotalRevenue(BigDecimal totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    // Getter for total orders
    public Long getTotalOrders() {
        return totalOrders;
    }

    // Setter for total orders
    public void setTotalOrders(Long totalOrders) {
        this.totalOrders = totalOrders;
    }

    // Getter for average order value
    public BigDecimal getAverageOrderValue() {
        return averageOrderValue;
    }

    // Setter for average order value
    public void setAverageOrderValue(BigDecimal averageOrderValue) {
        this.averageOrderValue = averageOrderValue;
    }
}