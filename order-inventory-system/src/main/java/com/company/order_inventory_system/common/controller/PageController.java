package com.company.order_inventory_system.common.controller;

import com.company.order_inventory_system.common.model.ModuleLink;
import com.company.order_inventory_system.common.model.TeamMember;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class PageController {

    @GetMapping("/")
    public String homePage(Model model) {

        List<TeamMember> members = List.of(

                new TeamMember(
                        "Kumar Aditya Pratap",
                        "Lead Systems Architect & Logistics Orchestrator",
                        "/images/Kumar.png",
                        List.of(
                                new ModuleLink(
                                        "Order",
                                        "/order-module/dashboard"
                                ),
                                new ModuleLink(
                                        "OrderItem",
                                        "/orderItem-module/dashboard"
                                ),
                                new ModuleLink(
                                        "Shipment",
                                        "/shipment-module/dashboard"
                                )
                        )
                ),

                new TeamMember(
                        "Akshaya Priya D",
                        "Principal Security Engineer & Customer Relations Lead",
                        "/images/Akshaya.jpeg",
                        List.of(
                                new ModuleLink(
                                        "Customer",
                                        "/customer-module/dashboard"
                                ),
                                new ModuleLink(
                                        "Security Configuration",
                                         "#"
                                )
                        )
                ),

                new TeamMember(
                        "Sneha Angapparaj",
                        "Head of Product & Business Intelligence Analytics",
                        "/images/Sneha.jpeg",
                        List.of(
                                new ModuleLink(
                                        "Product",
                                        "/product-module/dashboard"
                                ),
                                new ModuleLink(
                                        "Report",
                                        "/report-module/dashboard"
                                )
                        )
                ),

                new TeamMember(
                        "Akhil Puri",
                        "Director of Store Operations & Inventory Control",
                        "/images/Akhil.png",
                        List.of(
                                new ModuleLink(
                                        "Store",
                                        "/store-module/dashboard"
                                ),
                                new ModuleLink(
                                        "Inventory",
                                        "/inventory-module/dashboard"
                                )
                        )
                )
        );

        model.addAttribute(
                "projectTitle",
                "Order Inventory System"
        );

        model.addAttribute(
                "projectDescription",
                "The <strong>Order Inventory System (OIS)</strong> is a high-performance management platform " +
                        "designed to streamline retail operations. It provides end-to-end automation for store " +
                        "management, real-time inventory tracking, customer profiling, order lifecycle processing, " +
                        "and shipment logistics, supported by role-based security and business intelligence reporting."
        );

        model.addAttribute(
                "teamMembers",
                members
        );

        return "index";
    }
}