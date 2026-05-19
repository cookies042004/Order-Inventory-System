package com.company.order_inventory_system.report.repository;

import com.company.order_inventory_system.report.dto.InventoryRiskResponse;
import com.company.order_inventory_system.report.dto.SalesSummaryResponse;
import com.company.order_inventory_system.report.dto.TopCustomerResponse;

import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<
        com.company.order_inventory_system.order.entity.Order,
        Integer> {

    // Sales Summary Report Query
    // Calculates:
    // 1. Total Revenue
    // 2. Total Orders
    // 3. Average Order Value

    @Query("""

            SELECT new com.company.order_inventory_system.report.dto.SalesSummaryResponse(

                SUM(oi.unitPrice * oi.quantity),

                COUNT(DISTINCT o.orderId),

                SUM(oi.unitPrice * oi.quantity)
                / COUNT(DISTINCT o.orderId)

            )

            FROM Order o

            JOIN OrderItem oi
                ON o.orderId = oi.orderId

            WHERE o.orderStatus =
                com.company.order_inventory_system.order.enums.OrderStatus.COMPLETE

            AND o.orderTms BETWEEN :fromDate AND :toDate

            AND o.storeId = :storeId

            """)
    SalesSummaryResponse getSalesSummaryReport(

            // Start date filter
            @Param("fromDate")
            LocalDateTime fromDate,

            // End date filter
            @Param("toDate")
            LocalDateTime toDate,

            // Store filter
            @Param("storeId")
            Integer storeId
    );

    // Top Customers Report Query
    @Query("""

            SELECT new com.company.order_inventory_system.report.dto.TopCustomerResponse(

                c.fullName,

                COUNT(DISTINCT o.orderId),

                SUM(oi.unitPrice * oi.quantity)

            )

            FROM Customer c

            JOIN Order o
                ON c.customerId = o.customerId

            JOIN OrderItem oi
                ON o.orderId = oi.orderId

            WHERE o.orderStatus =
                com.company.order_inventory_system.order.enums.OrderStatus.COMPLETE

            GROUP BY c.customerId, c.fullName

            ORDER BY SUM(oi.unitPrice * oi.quantity) DESC

            """)
    List<TopCustomerResponse> getTopCustomersReport(
            Pageable pageable
    );

    // Inventory Risk Report Query
    @Query("""

            SELECT new com.company.order_inventory_system.report.dto.InventoryRiskResponse(

                i.product.productId,

                i.productInventory,

                COALESCE(SUM(oi.quantity), 0),

                CASE

                    WHEN i.productInventory < 5
                        THEN 'CRITICAL'

                    WHEN i.productInventory < COALESCE(SUM(oi.quantity), 0)
                        THEN 'RISK'

                    ELSE 'LOW'

                END

            )

            FROM Inventory i

            LEFT JOIN OrderItem oi
                ON i.product.productId = oi.productId

            GROUP BY
                i.product.productId,
                i.productInventory

            ORDER BY i.productInventory ASC

            """)
    List<InventoryRiskResponse> getInventoryRiskReport();
}