package com.company.order_inventory_system.report.dto;

// DTO for Inventory Risk Report
public class InventoryRiskResponse {

    // Product ID
    private Integer productId;

    // Current inventory stock
    private Integer currentStock;

    // Total quantity sold
    private Long totalSold;

    // Inventory risk level
    private String riskLevel;

    // Constructor used by JPQL query
    public InventoryRiskResponse(
            Integer productId,
            Integer currentStock,
            Long totalSold,
            String riskLevel
    ) {
        this.productId = productId;
        this.currentStock = currentStock;
        this.totalSold = totalSold;
        this.riskLevel = riskLevel;
    }

    // Getter methods
    public Integer getProductId() {
        return productId;
    }

    public Integer getCurrentStock() {
        return currentStock;
    }

    public Long getTotalSold() {
        return totalSold;
    }

    public String getRiskLevel() {
        return riskLevel;
    }
}