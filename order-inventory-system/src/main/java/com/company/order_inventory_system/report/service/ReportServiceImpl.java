package com.company.order_inventory_system.report.service;

import com.company.order_inventory_system.report.dto.SalesSummaryResponse;

import com.company.order_inventory_system.report.repository.ReportRepository;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

import com.company.order_inventory_system.report.dto.TopCustomerResponse;

import org.springframework.data.domain.PageRequest;

import org.springframework.data.domain.Pageable;

import java.util.List;

import com.company.order_inventory_system.report.dto.InventoryRiskResponse;

import com.company.order_inventory_system.report.dto.InventoryRiskResponse;

@Service
public class ReportServiceImpl implements ReportService {

    // Repository dependency
    private final ReportRepository reportRepository;

    // Constructor injection
    public ReportServiceImpl(
            ReportRepository reportRepository
    ) {
        this.reportRepository = reportRepository;
    }

    // Fetches sales summary report
    @Override
    public SalesSummaryResponse getSalesSummaryReport(

            LocalDateTime fromDate,

            LocalDateTime toDate,

            Integer storeId
    ) {

        // Calls repository query
        return reportRepository.getSalesSummaryReport(
                fromDate,
                toDate,
                storeId
        );

    }

    // Returns top customers report
    @Override
    public List<TopCustomerResponse> getTopCustomersReport(
            Integer limit
    ) {

        // Creates pageable object for TOP N records
        Pageable pageable =
                PageRequest.of(0, limit);

        // Calls repository query
        return reportRepository
                .getTopCustomersReport(pageable);
    }

    // Returns inventory risk report
    @Override
    public List<InventoryRiskResponse> getInventoryRiskReport() {

        // Calls repository query
        return reportRepository.getInventoryRiskReport();
    }

}