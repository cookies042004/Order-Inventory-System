package com.company.order_inventory_system.report.dto;

import java.math.BigDecimal;

// DTO for Top Customers Report response
public class TopCustomerResponse {

    // Customer full name
    private String customerName;

    // Total completed orders
    private Long totalOrders;

    // Total amount spent
    private BigDecimal totalSpend;

    // Constructor used by JPQL query
    public TopCustomerResponse(
            String customerName,
            Long totalOrders,
            BigDecimal totalSpend
    ) {
        this.customerName = customerName;
        this.totalOrders = totalOrders;
        this.totalSpend = totalSpend;
    }

    // Getter methods
    public String getCustomerName() {
        return customerName;
    }

    public Long getTotalOrders() {
        return totalOrders;
    }

    public BigDecimal getTotalSpend() {
        return totalSpend;
    }
}