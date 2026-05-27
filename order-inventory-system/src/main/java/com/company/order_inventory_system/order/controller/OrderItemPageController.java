package com.company.order_inventory_system.order.controller;

import com.company.order_inventory_system.common.model.FormField;
import com.company.order_inventory_system.common.ui.service.EndpointExecutionService;
import com.company.order_inventory_system.order.dto.OrderItemRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@Controller
public class OrderItemPageController {

    private final EndpointExecutionService endpointExecutionService;

    @Value("${order.username}")
    private String orderUsername;

    @Value("${order.password}")
    private String orderPassword;

    public OrderItemPageController(EndpointExecutionService endpointExecutionService) {
        this.endpointExecutionService = endpointExecutionService;
    }

    // DASHBOARD
    @GetMapping("/orderItem-module/dashboard")
    public String orderItemDashboardPage() {
        return "fragments/orderItem-module";
    }

    // GET ALL ORDER ITEMS
    @GetMapping("/orderItem-module/endpoints/get-all-order-items")
    public String getAllOrderItemsPage(Model model) {
        model.addAttribute("endpointTitle", "Get All Order Items");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/order-items");
        model.addAttribute("description", "Fetch all registered order line item records");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/get-all-order-items/execute");
        return "endpoint-details";
    }

    @PostMapping("/orderItem-module/endpoints/get-all-order-items/execute")
    public String executeGetAllOrderItems(Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/order-items",
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get All Order Items");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/order-items");
        model.addAttribute("description", "Fetch all registered order line item records");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/get-all-order-items/execute");
        return "endpoint-details";
    }

    // GET ORDER ITEM BY ID
    @GetMapping("/orderItem-module/endpoints/get-order-item-by-id")
    public String getOrderItemByIdPage(Model model) {
        model.addAttribute("endpointTitle", "Get Order Item By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/order-items/{orderId}");
        model.addAttribute("description", "Fetch order item details using order ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/get-order-item-by-id/execute");
        return "endpoint-details";
    }

    @PostMapping("/orderItem-module/endpoints/get-order-item-by-id/execute")
    public String executeGetOrderItemById(@RequestParam Integer orderId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeGetRequest(
                "http://localhost:8080/api/order-items/" + orderId,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Get Order Item By ID");
        model.addAttribute("httpMethod", "GET");
        model.addAttribute("apiUrl", "/api/order-items/{orderId}");
        model.addAttribute("description", "Fetch order item details using order ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/get-order-item-by-id/execute");
        return "endpoint-details";
    }

    private List<FormField> getOrderItemFields() {
        return List.of(
                new FormField("lineItemId", "Line Item ID", "number", true),
                new FormField("productId", "Product ID", "number", true),
                new FormField("unitPrice", "Unit Price", "number", true),
                new FormField("quantity", "Quantity", "number", true),
                new FormField("shipmentId", "Shipment ID", "number", false)
        );
    }

    // CREATE ORDER ITEM
    @GetMapping("/orderItem-module/endpoints/create-order-item")
    public String createOrderItemPage(Model model) {
        model.addAttribute("endpointTitle", "Create Order Item");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/order-items");
        model.addAttribute("description", "Add a new line item to an order");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/create-order-item/execute");
        model.addAttribute("fields", getOrderItemFields());
        return "endpoint-details";
    }

    @PostMapping("/orderItem-module/endpoints/create-order-item/execute")
    public String executeCreateOrderItem(
            @RequestParam Integer lineItemId,
            @RequestParam Integer productId,
            @RequestParam BigDecimal unitPrice,
            @RequestParam Integer quantity,
            @RequestParam(required = false) Integer shipmentId,
            Model model
    ) {
        OrderItemRequest request = new OrderItemRequest();
        request.setLineItemId(lineItemId);
        request.setProductId(productId);
        request.setUnitPrice(unitPrice);
        request.setQuantity(quantity);
        request.setShipmentId(shipmentId);

        Map<String, Object> response = endpointExecutionService.executePostRequest(
                "http://localhost:8080/api/order-items",
                request,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Create Order Item");
        model.addAttribute("httpMethod", "POST");
        model.addAttribute("apiUrl", "/api/order-items");
        model.addAttribute("description", "Add a new line item to an order");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/create-order-item/execute");
        model.addAttribute("fields", getOrderItemFields());
        return "endpoint-details";
    }

    // UPDATE ORDER ITEM
    @GetMapping("/orderItem-module/endpoints/update-order-item")
    public String updateOrderItemPage(Model model) {
        model.addAttribute("endpointTitle", "Update Order Item");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/order-items/{orderId}");
        model.addAttribute("description", "Update details of an existing order line item");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/update-order-item/execute");
        model.addAttribute("fields", getOrderItemFields());
        return "endpoint-details";
    }

    @PostMapping("/orderItem-module/endpoints/update-order-item/execute")
    public String executeUpdateOrderItem(
            @RequestParam Integer orderId,
            @RequestParam Integer lineItemId,
            @RequestParam Integer productId,
            @RequestParam BigDecimal unitPrice,
            @RequestParam Integer quantity,
            @RequestParam(required = false) Integer shipmentId,
            Model model
    ) {
        OrderItemRequest request = new OrderItemRequest();
        request.setLineItemId(lineItemId);
        request.setProductId(productId);
        request.setUnitPrice(unitPrice);
        request.setQuantity(quantity);
        request.setShipmentId(shipmentId);

        Map<String, Object> response = endpointExecutionService.executePutRequest(
                "http://localhost:8080/api/order-items/" + orderId,
                request,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Update Order Item");
        model.addAttribute("httpMethod", "PUT");
        model.addAttribute("apiUrl", "/api/order-items/{orderId}");
        model.addAttribute("description", "Update details of an existing order line item");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/update-order-item/execute");
        model.addAttribute("fields", getOrderItemFields());
        return "endpoint-details";
    }

    // DELETE ORDER ITEM
    @GetMapping("/orderItem-module/endpoints/delete-order-item")
    public String deleteOrderItemPage(Model model) {
        model.addAttribute("endpointTitle", "Delete Order Item");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/order-items/{orderId}");
        model.addAttribute("description", "Delete an order line item by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/delete-order-item/execute");
        return "endpoint-details";
    }

    @PostMapping("/orderItem-module/endpoints/delete-order-item/execute")
    public String executeDeleteOrderItem(@RequestParam Integer orderId, Model model) {
        Map<String, Object> response = endpointExecutionService.executeDeleteRequest(
                "http://localhost:8080/api/order-items/" + orderId,
                orderUsername,
                orderPassword
        );
        model.addAllAttributes(response);
        model.addAttribute("endpointTitle", "Delete Order Item");
        model.addAttribute("httpMethod", "DELETE");
        model.addAttribute("apiUrl", "/api/order-items/{orderId}");
        model.addAttribute("description", "Delete an order line item by ID");
        model.addAttribute("requiresInput", true);
        model.addAttribute("inputLabel", "Order ID");
        model.addAttribute("inputName", "orderId");
        model.addAttribute("executeUrl", "/orderItem-module/endpoints/delete-order-item/execute");
        return "endpoint-details";
    }
}
