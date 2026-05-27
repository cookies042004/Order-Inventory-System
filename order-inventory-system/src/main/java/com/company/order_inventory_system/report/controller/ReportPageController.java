package com.company.order_inventory_system.report.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@Controller
public class ReportPageController {

    private final EndpointExecutionService endpointExecutionService;

    @Value("${report.username}")
    private String reportUsername;

    @Value("${report.password}")
    private String reportPassword;

    public ReportPageController(EndpointExecutionService endpointExecutionService) {
        this.endpointExecutionService = endpointExecutionService;
    }

    // DASHBOARD
    @GetMapping("/report-module/dashboard")
    public String reportDashboardPage() {
        return "fragments/report-module";
    }

    // SALES SUMMARY REPORT
    @GetMapping("/report-module/endpoints/get-sales-summary")
    public String getSalesSummaryPage(Model model) {
        List<FormField> fields = List.of(
                new FormField("from", "Start Date (from)", "datetime-local", true),
                new FormField("to", "End Date (to)", "datetime-local", true),
                new FormField("storeId", "Store ID", "number", true)
        );
        model.addAttribute("endpointTitle", "Sales Summary Report");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/reports/sales/summary?from={from}&to={to}&storeId={storeId}");
        model.addAttribute("description", "Generate revenue, total orders and average order value metrics");
        model.addAttribute("executeUrl", "/report-module/endpoints/get-sales-summary/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    @PostMapping("/report-module/endpoints/get-sales-summary/execute")
    public String executeGetSalesSummary(
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam Integer storeId,
            Model model
    ) {
        List<FormField> fields = List.of(
                new FormField("from", "Start Date (from)", "datetime-local", true),
                new FormField("to", "End Date (to)", "datetime-local", true),
                new FormField("storeId", "Store ID", "number", true)
        );

        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/reports/sales/summary?from=" + from + "&to=" + to + "&storeId=" + storeId,
                reportUsername,
                reportPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Sales Summary Report");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/reports/sales/summary?from={from}&to={to}&storeId={storeId}");
        model.addAttribute("description", "Generate revenue, total orders and average order value metrics");
        model.addAttribute("executeUrl", "/report-module/endpoints/get-sales-summary/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    // TOP CUSTOMERS REPORT
    @GetMapping("/report-module/endpoints/get-top-customers")
    public String getTopCustomersPage(Model model) {
        List<FormField> fields = List.of(
                new FormField("limit", "Top Customers Limit", "number", true)
        );
        model.addAttribute("endpointTitle", "Top Customers Report");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/reports/customers/top?limit={limit}");
        model.addAttribute("description", "Fetch top customers ranked by their purchase spending");
        model.addAttribute("executeUrl", "/report-module/endpoints/get-top-customers/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    @PostMapping("/report-module/endpoints/get-top-customers/execute")
    public String executeGetTopCustomers(@RequestParam Integer limit, Model model) {
        List<FormField> fields = List.of(
                new FormField("limit", "Top Customers Limit", "number", true)
        );

        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/reports/customers/top?limit=" + limit,
                reportUsername,
                reportPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Top Customers Report");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/reports/customers/top?limit={limit}");
        model.addAttribute("description", "Fetch top customers ranked by their purchase spending");
        model.addAttribute("executeUrl", "/report-module/endpoints/get-top-customers/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    // INVENTORY RISK REPORT
    @GetMapping("/report-module/endpoints/get-inventory-risk")
    public String getInventoryRiskPage(Model model) {
        model.addAttribute("endpointTitle", "Inventory Risk Report");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/reports/inventory/risk");
        model.addAttribute("description", "Identify products that are low or out of stock across warehouses");
        model.addAttribute("executeUrl", "/report-module/endpoints/get-inventory-risk/execute");
        return "endpoint-details";
    }

    @PostMapping("/report-module/endpoints/get-inventory-risk/execute")
    public String executeGetInventoryRisk(Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/reports/inventory/risk",
                reportUsername,
                reportPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Inventory Risk Report");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/reports/inventory/risk");
        model.addAttribute("description", "Identify products that are low or out of stock across warehouses");
        model.addAttribute("executeUrl", "/report-module/endpoints/get-inventory-risk/execute");
        return "endpoint-details";
    }
}
