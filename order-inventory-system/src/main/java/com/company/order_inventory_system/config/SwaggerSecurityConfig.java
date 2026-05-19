package com.company.order_inventory_system.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;

import org.springframework.context.annotation.Configuration;

@Configuration

@OpenAPIDefinition

@SecurityScheme(
        name = "basicAuthScheme",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)

public class SwaggerSecurityConfig {
}