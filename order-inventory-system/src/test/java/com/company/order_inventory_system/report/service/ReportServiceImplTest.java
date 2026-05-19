package com.company.order_inventory_system.report.service;

import com.company.order_inventory_system.report.dto.InventoryRiskResponse;
import com.company.order_inventory_system.report.dto.SalesSummaryResponse;
import com.company.order_inventory_system.report.dto.TopCustomerResponse;
import com.company.order_inventory_system.report.repository.ReportRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class ReportServiceImplTest {

    @Mock
    private ReportRepository reportRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Test Sales Summary Report")
    void testSalesSummaryReport() {

        SalesSummaryResponse response =
                new SalesSummaryResponse(
                        BigDecimal.valueOf(50000),
                        10L,
                        BigDecimal.valueOf(5000)
                );

        when(reportRepository.getSalesSummaryReport(
                LocalDateTime.of(2026, 5, 1, 0, 0),
                LocalDateTime.of(2026, 5, 30, 23, 59),
                1
        )).thenReturn(response);

        SalesSummaryResponse result =
                reportService.getSalesSummaryReport(
                        LocalDateTime.of(2026, 5, 1, 0, 0),
                        LocalDateTime.of(2026, 5, 30, 23, 59),
                        1
                );

        assertNotNull(result);
        assertEquals(BigDecimal.valueOf(50000), result.getTotalRevenue());
    }

    @Test
    @DisplayName("Test Top Customers Report")
    void testTopCustomersReport() {

        when(reportRepository.getTopCustomersReport(any()))
                .thenReturn(List.of());

        List<TopCustomerResponse> result =
                reportService.getTopCustomersReport(5);

        assertNotNull(result);
    }

    @Test
    @DisplayName("Test Inventory Risk Report")
    void testInventoryRiskReport() {

        when(reportRepository.getInventoryRiskReport())
                .thenReturn(List.of());

        List<InventoryRiskResponse> result =
                reportService.getInventoryRiskReport();

        assertNotNull(result);
    }

    // Tests sales summary when repository returns null
    @Test
    @DisplayName("Test Empty Sales Summary Report")
    void testSalesSummaryReport_EmptyResponse() {

        // Mock null repository response
        when(reportRepository.getSalesSummaryReport(
                any(),
                any(),
                any()
        )).thenReturn(null);

        // Call service method
        SalesSummaryResponse result =
                reportService.getSalesSummaryReport(
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1
                );

        // Validate null response
        assertEquals(null, result);
    }

    // Tests top customers report with empty data
    @Test
    @DisplayName("Test Empty Top Customers Report")
    void testTopCustomersReport_EmptyList() {

        // Mock empty repository response
        when(reportRepository.getTopCustomersReport(any()))
                .thenReturn(List.of());

        // Call service method
        List<TopCustomerResponse> result =
                reportService.getTopCustomersReport(5);

        // Validate empty list
        assertNotNull(result);

        assertEquals(0, result.size());
    }
    // Tests inventory risk report with no inventory data
    @Test
    @DisplayName("Test Empty Inventory Risk Report")
    void testInventoryRiskReport_EmptyList() {

        // Mock empty repository response
        when(reportRepository.getInventoryRiskReport())
                .thenReturn(List.of());

        // Call service method
        List<InventoryRiskResponse> result =
                reportService.getInventoryRiskReport();

        // Validate empty list
        assertNotNull(result);

        assertEquals(0, result.size());
    }

    // Tests sales summary response values
    @Test
    @DisplayName("Validate Sales Summary Values")
    void testSalesSummaryReport_ValidateFields() {

        // Sample response object
        SalesSummaryResponse response =
                new SalesSummaryResponse(
                        BigDecimal.valueOf(75000),
                        15L,
                        BigDecimal.valueOf(5000)
                );

        // Mock repository response
        when(reportRepository.getSalesSummaryReport(
                any(),
                any(),
                any()
        )).thenReturn(response);

        // Call service method
        SalesSummaryResponse result =
                reportService.getSalesSummaryReport(
                        LocalDateTime.now(),
                        LocalDateTime.now(),
                        1
                );

        // Validate response fields
        assertEquals(
                BigDecimal.valueOf(75000),
                result.getTotalRevenue()
        );

        assertEquals(
                15L,
                result.getTotalOrders()
        );

        assertEquals(
                BigDecimal.valueOf(5000),
                result.getAverageOrderValue()
        );
    }
}