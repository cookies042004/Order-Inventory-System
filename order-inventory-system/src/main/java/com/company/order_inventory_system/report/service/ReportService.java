package com.company.order_inventory_system.report.service;

import com.company.order_inventory_system.report.dto.SalesSummaryResponse;

import java.time.LocalDateTime;

import com.company.order_inventory_system.report.dto.TopCustomerResponse;

import com.company.order_inventory_system.report.dto.InventoryRiskResponse;
import java.util.List;

import com.company.order_inventory_system.report.dto.InventoryRiskResponse;
// Service interface for report operations
public interface ReportService {

    // Fetches sales summary report
    SalesSummaryResponse getSalesSummaryReport(

            // Start date filter
            LocalDateTime fromDate,

            // End date filter
            LocalDateTime toDate,

            // Store filter
            Integer storeId
    );

    // Returns top customers report
    List<TopCustomerResponse> getTopCustomersReport(
            Integer limit
    );

    // Returns inventory risk report
    List<InventoryRiskResponse> getInventoryRiskReport();

}