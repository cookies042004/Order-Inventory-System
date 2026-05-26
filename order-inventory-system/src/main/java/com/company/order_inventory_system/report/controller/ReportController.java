package com.company.order_inventory_system.report.controller;

import com.company.order_inventory_system.report.dto.SalesSummaryResponse;

import com.company.order_inventory_system.report.service.ReportService;

import io.swagger.v3.oas.annotations.Operation;

import io.swagger.v3.oas.annotations.responses.ApiResponse;

import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.format.annotation.DateTimeFormat;

import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

import com.company.order_inventory_system.report.dto.TopCustomerResponse;

import com.company.order_inventory_system.report.dto.InventoryRiskResponse;

import java.util.List;

import org.springframework.http.ResponseEntity;

@Tag(
        name = "Report Controller",
        description = "APIs for high impact reports"
)

@RestController
@RequestMapping("/api/reports")
public class ReportController {

    // Service dependency
    private final ReportService reportService;

    // Constructor injection
    public ReportController(
            ReportService reportService
    ) {
        this.reportService = reportService;
    }

    // Sales summary report API
    @Operation(
            summary = "Sales Summary Report",
            description = "Returns revenue, total orders and average order value"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Sales report generated successfully"
    )

    @GetMapping("/sales/summary")
    public ResponseEntity<?> getSalesSummary(

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime from,

            @RequestParam
            @DateTimeFormat(
                    iso = DateTimeFormat.ISO.DATE_TIME
            )
            LocalDateTime to,

            @RequestParam
            Integer storeId
    ) {

        if (from.isAfter(to)) {
            return ResponseEntity.badRequest()
                    .body("From date cannot be greater than To date");
        }

        if (storeId <= 0) {
            return ResponseEntity.badRequest()
                    .body("Store ID must be greater than 0");
        }

        return ResponseEntity.ok(
                reportService.getSalesSummaryReport(
                        from,
                        to,
                        storeId
                )
        );
    }

    // Top customers report API
    @Operation(
            summary = "Top Customers Report",
            description = "Returns top customers based on spending"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Top customers report generated successfully"
    )

    @GetMapping("/customers/top")
    public ResponseEntity<?>  getTopCustomersReport(

            @RequestParam(defaultValue = "10")
            Integer limit
    ) {

        if (limit <= 0) {
            return ResponseEntity.badRequest()
                    .body("Limit must be greater than 0");
        }

        return ResponseEntity.ok(
                reportService.getTopCustomersReport(limit)
        );
    }

    // Inventory Risk Report
    @Operation(
            summary = "Inventory Risk Report",
            description = "Returns inventory stock risk analysis"
    )

    @ApiResponse(
            responseCode = "200",
            description = "Inventory risk report generated successfully"
    )

    @GetMapping("/inventory/risk")
    public ResponseEntity<List<InventoryRiskResponse>>
    getInventoryRiskReport() {

        return ResponseEntity.ok(
                reportService.getInventoryRiskReport()
        );
    }
}