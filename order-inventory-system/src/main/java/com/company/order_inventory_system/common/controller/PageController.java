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
                        "/images/user-default.png",
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
                        "/images/user-default.png",
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
                        "/images/user-default.png",
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
                        "/images/user-default.png",
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
                "Enterprise Inventory & Order Management Platform"
        );

        model.addAttribute(
                "teamMembers",
                members
        );

        return "index";
    }
}