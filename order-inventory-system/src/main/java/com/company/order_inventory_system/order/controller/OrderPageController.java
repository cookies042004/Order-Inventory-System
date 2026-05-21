package com.company.order_inventory_system.order.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.order.dto.OrderRequest;
import com.company.order_inventory_system.order.enums.OrderStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@Controller
public class OrderPageController {

    private final EndpointExecutionService endpointExecutionService;

    @Value("${order.username}")
    private String orderUsername;

    @Value("${order.password}")
    private String orderPassword;

    public OrderPageController(EndpointExecutionService endpointExecutionService) {
        this.endpointExecutionService = endpointExecutionService;
    }

    // DASHBOARD
    @GetMapping("/order-module/dashboard")
    public String orderDashboardPage() {
        return "fragments/order-module";
    }

    // GET ALL ORDERS
    @GetMapping("/order-module/endpoints/get-all-orders")
    public String getAllOrdersPage(Model model) {
        model.addAttribute("endpointTitle", "Get All Orders");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders");
        model.addAttribute("description", "Fetch all registered customer orders");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-all-orders/execute");
        return "endpoint-details";
    }

    @PostMapping("/order-module/endpoints/get-all-orders/execute")
    public String executeGetAllOrders(Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/orders",
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get All Orders");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders");
        model.addAttribute("description", "Fetch all registered customer orders");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-all-orders/execute");
        return "endpoint-details";
    }

    // GET ORDER BY ID
    @GetMapping("/order-module/endpoints/get-order-by-id")
    public String getOrderByIdPage(Model model) {
        model.addAttribute("endpointTitle", "Get Order By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/{orderId}");
        model.addAttribute("description", "Fetch order details using order ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-order-by-id/execute");
        return "endpoint-details";
    }

    @PostMapping("/order-module/endpoints/get-order-by-id/execute")
    public String executeGetOrderById(@RequestParam Integer orderId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/orders/" + orderId,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Order By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/{orderId}");
        model.addAttribute("description", "Fetch order details using order ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-order-by-id/execute");
        return "endpoint-details";
    }

    private List<FormField> getOrderFields() {
        return List.of(
                new FormField("orderTms", "Order Timestamp", "datetime-local", true),
                new FormField("customerId", "Customer ID", "number", true),
                new FormField("storeId", "Store ID", "number", true),
                new FormField("orderStatus", "Order Status", "select", true, List.of("OPEN", "PAID", "SHIPPED", "COMPLETE", "CANCELLED", "REFUNDED"))
        );
    }

    // CREATE ORDER
    @GetMapping("/order-module/endpoints/create-order")
    public String createOrderPage(Model model) {
        model.addAttribute("endpointTitle", "Create Order");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/orders");
        model.addAttribute("description", "Create a new customer order");
        model.addAttribute("executeUrl", "/order-module/endpoints/create-order/execute");
        model.addAttribute("fields", getOrderFields());
        return "endpoint-details";
    }

    @PostMapping("/order-module/endpoints/create-order/execute")
    public String executeCreateOrder(
            @RequestParam String orderTms,
            @RequestParam Integer customerId,
            @RequestParam Integer storeId,
            @RequestParam String orderStatus,
            Model model
    ) {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(customerId);
        request.setStoreId(storeId);
        request.setOrderStatus(OrderStatus.valueOf(orderStatus));

        // datetime-local parses to: 2026-05-21T13:40
        try {
            request.setOrderTms(LocalDateTime.parse(orderTms));
        } catch (Exception e) {
            // fallback format if browser submits slightly differently
            try {
                request.setOrderTms(LocalDateTime.parse(orderTms, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
            } catch (Exception ex) {
                // let it fail on bean validation if parsing failed completely
            }
        }

        Map<String, Object> response = endpointExecutionService.executePostRequest(
                "http://localhost:8080/api/orders",
                request,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Create Order");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/orders");
        model.addAttribute("description", "Create a new customer order");
        model.addAttribute("executeUrl", "/order-module/endpoints/create-order/execute");
        model.addAttribute("fields", getOrderFields());
        return "endpoint-details";
    }

    // UPDATE ORDER
    @GetMapping("/order-module/endpoints/update-order")
    public String updateOrderPage(Model model) {
        model.addAttribute("endpointTitle", "Update Order");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/orders/{orderId}");
        model.addAttribute("description", "Update details of an existing order");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/order-module/endpoints/update-order/execute");
        model.addAttribute("fields", getOrderFields());
        return "endpoint-details";
    }

    @PostMapping("/order-module/endpoints/update-order/execute")
    public String executeUpdateOrder(
            @RequestParam Integer orderId,
            @RequestParam String orderTms,
            @RequestParam Integer customerId,
            @RequestParam Integer storeId,
            @RequestParam String orderStatus,
            Model model
    ) {
        OrderRequest request = new OrderRequest();
        request.setCustomerId(customerId);
        request.setStoreId(storeId);
        request.setOrderStatus(OrderStatus.valueOf(orderStatus));

        try {
            request.setOrderTms(LocalDateTime.parse(orderTms));
        } catch (Exception e) {
            try {
                request.setOrderTms(LocalDateTime.parse(orderTms, DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss")));
            } catch (Exception ex) {
                // ignore and let it fail on bean validation
            }
        }

        Map<String, Object> response = endpointExecutionService.executePutRequest(
                "http://localhost:8080/api/orders/" + orderId,
                request,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Update Order");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/orders/{orderId}");
        model.addAttribute("description", "Update details of an existing order");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/order-module/endpoints/update-order/execute");
        model.addAttribute("fields", getOrderFields());
        return "endpoint-details";
    }

    // DELETE ORDER
    @GetMapping("/order-module/endpoints/delete-order")
    public String deleteOrderPage(Model model) {
        model.addAttribute("endpointTitle", "Delete Order");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/orders/{orderId}");
        model.addAttribute("description", "Delete order record by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/order-module/endpoints/delete-order/execute");
        return "endpoint-details";
    }

    @PostMapping("/order-module/endpoints/delete-order/execute")
    public String executeDeleteOrder(@RequestParam Integer orderId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeDeleteRequest(
                "http://localhost:8080/api/orders/" + orderId,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Delete Order");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/orders/{orderId}");
        model.addAttribute("description", "Delete order record by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/order-module/endpoints/delete-order/execute");
        return "endpoint-details";
    }

    // GET ORDERS BY CUSTOMER
    @GetMapping("/order-module/endpoints/get-orders-by-customer")
    public String getOrdersByCustomerPage(Model model) {
        model.addAttribute("endpointTitle", "Get Orders By Customer");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/customer/{customerId}");
        model.addAttribute("description", "Fetch all orders placed by customer");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-orders-by-customer/execute");
        return "endpoint-details";
    }

    @PostMapping("/order-module/endpoints/get-orders-by-customer/execute")
    public String executeGetOrdersByCustomer(@RequestParam Integer customerId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/orders/customer/" + customerId,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Orders By Customer");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/customer/{customerId}");
        model.addAttribute("description", "Fetch all orders placed by customer");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Customer ID");
        model.addAttribute("inputName", "customerId");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-orders-by-customer/execute");
        return "endpoint-details";
    }

    // GET ORDERS BY STORE
    @GetMapping("/order-module/endpoints/get-orders-by-store")
    public String getOrdersByStorePage(Model model) {
        model.addAttribute("endpointTitle", "Get Orders By Store");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/store/{storeId}");
        model.addAttribute("description", "Fetch all orders placed at a specific store");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-orders-by-store/execute");
        return "endpoint-details";
    }

    @PostMapping("/order-module/endpoints/get-orders-by-store/execute")
    public String executeGetOrdersByStore(@RequestParam Integer storeId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/orders/store/" + storeId,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Orders By Store");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/store/{storeId}");
        model.addAttribute("description", "Fetch all orders placed at a specific store");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Store ID");
        model.addAttribute("inputName", "storeId");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-orders-by-store/execute");
        return "endpoint-details";
    }

    // GET ORDERS BY STATUS
    @GetMapping("/order-module/endpoints/get-orders-by-status")
    public String getOrdersByStatusPage(Model model) {
        List<FormField> fields = List.of(
                new FormField("status", "Order Status", "select", true, List.of("OPEN", "PAID", "SHIPPED", "COMPLETE", "CANCELLED", "REFUNDED"))
        );
        model.addAttribute("endpointTitle", "Get Orders By Status");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/status/{status}");
        model.addAttribute("description", "Filter and fetch orders by their execution status");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-orders-by-status/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    @PostMapping("/order-module/endpoints/get-orders-by-status/execute")
    public String executeGetOrdersByStatus(@RequestParam String status, Model model) {
        List<FormField> fields = List.of(
                new FormField("status", "Order Status", "select", true, List.of("OPEN", "PAID", "SHIPPED", "COMPLETE", "CANCELLED", "REFUNDED"))
        );
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/orders/status/" + status,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Orders By Status");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/status/{status}");
        model.addAttribute("description", "Filter and fetch orders by their execution status");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-orders-by-status/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    // GET ORDERS BY DATE RANGE
    @GetMapping("/order-module/endpoints/get-orders-by-date-range")
    public String getOrdersByDateRangePage(Model model) {
        List<FormField> fields = List.of(
                new FormField("start", "Start Timestamp", "datetime-local", true),
                new FormField("end", "End Timestamp", "datetime-local", true)
        );
        model.addAttribute("endpointTitle", "Get Orders By Date Range");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/daterange?start={start}&end={end}");
        model.addAttribute("description", "Query and fetch orders within a specific date-time range");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-orders-by-date-range/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }

    @PostMapping("/order-module/endpoints/get-orders-by-date-range/execute")
    public String executeGetOrdersByDateRange(
            @RequestParam String start,
            @RequestParam String end,
            Model model
    ) {
        List<FormField> fields = List.of(
                new FormField("start", "Start Timestamp", "datetime-local", true),
                new FormField("end", "End Timestamp", "datetime-local", true)
        );

        // REST API requires ISO-formatted string or LocalDateTime.
        // Spring MVC/RestTemplate will handle standard string format if passed directly to search params.
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/orders/daterange?start=" + start + "&end=" + end,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Orders By Date Range");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/orders/daterange?start={start}&end={end}");
        model.addAttribute("description", "Query and fetch orders within a specific date-time range");
        model.addAttribute("executeUrl", "/order-module/endpoints/get-orders-by-date-range/execute");
        model.addAttribute("fields", fields);
        return "endpoint-details";
    }
}
