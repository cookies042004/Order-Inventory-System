package com.company.order_inventory_system.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {

        return new OpenAPI()
                .info(
                        new Info()
                                .title("Order Inventory Management System API")
                                .version("1.0")
                                .description("REST APIs for managing orders, " +
                                        "inventory, customers, products, shipments, and stores")
                );
    }
}