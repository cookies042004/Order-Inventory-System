package com.company.order_inventory_system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    /* =========================
       CUSTOMER CREDENTIALS
       ========================= */

    @Value("${customer.username}")
    private String customerUsername;

    @Value("${customer.password}")
    private String customerPassword;

    /* =========================
       PRODUCT CREDENTIALS
       ========================= */

    @Value("${product.username}")
    private String productUsername;

    @Value("${product.password}")
    private String productPassword;

    /* =========================
       ORDER CREDENTIALS
       ========================= */

    @Value("${order.username}")
    private String orderUsername;

    @Value("${order.password}")
    private String orderPassword;

    /* =========================
       SHIPMENT CREDENTIALS
       ========================= */

    @Value("${shipment.username}")
    private String shipmentUsername;

    @Value("${shipment.password}")
    private String shipmentPassword;

    /* =========================
       INVENTORY CREDENTIALS
       ========================= */

    @Value("${inventory.username}")
    private String inventoryUsername;

    @Value("${inventory.password}")
    private String inventoryPassword;

    /* =========================
       STORE CREDENTIALS
       ========================= */

    @Value("${store.username}")
    private String storeUsername;

    @Value("${store.password}")
    private String storePassword;

    /* =========================
       REPORT CREDENTIALS
       ========================= */

    @Value("${report.username}")
    private String reportUsername;

    @Value("${report.password}")
    private String reportPassword;


    /* =========================
       SECURITY FILTER CHAIN
       ========================= */

    @Bean
    public SecurityFilterChain securityFilterChain(
            HttpSecurity http) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        /* PUBLIC */

                        .requestMatchers(

                                "/",
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html",
                                "/login",

                                "/css/**",
                                "/js/**",
                                "/images/**",
                                "/favicon.ico",

                                "/error"
                        )
                        .permitAll()

                        .requestMatchers(
                                "/api/orders/**",
                                "/api/order-items/**"
                        )
                        .hasRole("ORDER")

                        /* MODULE PAGES */

                        .requestMatchers(
                                "/store-module",
                                "/store-module/**"
                        ).hasRole("STORE")

                        .requestMatchers(
                                "/inventory-module",
                                "/inventory-module/**"
                        ).hasRole("INVENTORY")

                        .requestMatchers(
                                "/customer-module",
                                "/customer-module/**"
                        ).hasRole("CUSTOMER")

                        .requestMatchers(
                                "/product-module",
                                "/product-module/**"
                        ).hasRole("PRODUCT")

                        .requestMatchers(
                                "/order-module",
                                "/order-module/**",
                                "/orderItem-module",
                                "/orderItem-module/**"
                        ).hasRole("ORDER")

                        .requestMatchers(
                                "/shipment-module",
                                "/shipment-module/**"
                        ).hasRole("SHIPMENT")

                        .requestMatchers(
                                "/report-module",
                                "/report-module/**"
                        ).hasRole("REPORT")

                        /* API ENDPOINTS */

                        .requestMatchers("/api/stores", "/api/stores/**")
                        .hasRole("STORE")

                        .requestMatchers("/api/inventory", "/api/inventory/**")
                        .hasRole("INVENTORY")

                        .requestMatchers("/api/customers", "/api/customers/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers("/api/products", "/api/products/**")
                        .hasRole("PRODUCT")

                        .requestMatchers(
                                "/api/orders",
                                "/api/orders/**",
                                "/api/order-items",
                                "/api/order-items/**"
                        ).hasRole("ORDER")

                        .requestMatchers("/api/shipments", "/api/shipments/**")
                        .hasRole("SHIPMENT")

                        .requestMatchers("/api/reports", "/api/reports/**")
                        .hasRole("REPORT")

                        .requestMatchers("/api/reports/**")
                        .hasRole("REPORT")

                        .anyRequest()
                        .authenticated()
                )

                /* HTTP BASIC */

                .httpBasic(httpBasic -> {})

                /* FORM LOGIN */

                .formLogin(form -> form

                        .loginPage("/login")

                        .defaultSuccessUrl("/", true)

                        .permitAll()
                )

                /* LOGOUT */

                .logout(logout -> logout

                        .logoutUrl("/logout")

                        .logoutSuccessUrl("/login?logout")

                        .permitAll()
                )

                /* ACCESS DENIED */

                .exceptionHandling(ex -> ex
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            String requestURI = request.getRequestURI();
                            if (requestURI != null && requestURI.startsWith("/api/")) {
                                response.setStatus(jakarta.servlet.http.HttpServletResponse.SC_FORBIDDEN);
                                response.setContentType("application/json;charset=UTF-8");
                                response.getWriter().write("{\"status\": 403, \"error\": \"Forbidden\", \"message\": \"Access Denied: You are not authorized to access this API endpoint.\"}");
                            } else {
                                request.getRequestDispatcher("/access-denied").forward(request, response);
                            }
                        })
                );

        return http.build();
    }


    /* =========================
       USER DETAILS
       ========================= */

    @Bean
    public UserDetailsService userDetailsService() {

        /* CUSTOMER USER */

        UserDetails customerUser =

                User.builder()

                        .username(customerUsername)

                        .password(
                                passwordEncoder()
                                        .encode(customerPassword)
                        )

                        .roles("CUSTOMER")

                        .build();

        /* PRODUCT USER */

        UserDetails productUser =

                User.builder()

                        .username(productUsername)

                        .password(
                                passwordEncoder()
                                        .encode(productPassword)
                        )

                        .roles("PRODUCT")

                        .build();

        /* ORDER USER */

        UserDetails orderUser =

                User.builder()

                        .username(orderUsername)

                        .password(
                                passwordEncoder()
                                        .encode(orderPassword)
                        )

                        .roles("ORDER")

                        .build();

        /* SHIPMENT USER */

        UserDetails shipmentUser =

                User.builder()

                        .username(shipmentUsername)

                        .password(
                                passwordEncoder()
                                        .encode(shipmentPassword)
                        )

                        .roles("SHIPMENT")

                        .build();

        /* INVENTORY USER */

        UserDetails inventoryUser =

                User.builder()

                        .username(inventoryUsername)

                        .password(
                                passwordEncoder()
                                        .encode(inventoryPassword)
                        )

                        .roles("INVENTORY")

                        .build();

        /* STORE USER */

        UserDetails storeUser =

                User.builder()

                        .username(storeUsername)

                        .password(
                                passwordEncoder()
                                        .encode(storePassword)
                        )

                        .roles("STORE")

                        .build();

        /* REPORT USER */

        UserDetails reportUser =

                User.builder()

                        .username(reportUsername)

                        .password(
                                passwordEncoder()
                                        .encode(reportPassword)
                        )

                        .roles("REPORT")

                        .build();


        return new InMemoryUserDetailsManager(

                customerUser,

                productUser,

                orderUser,

                shipmentUser,

                inventoryUser,

                storeUser,

                reportUser
        );
    }

    /* =========================
       PASSWORD ENCODER
       ========================= */

    @Bean
    public PasswordEncoder passwordEncoder() {

        return new BCryptPasswordEncoder();
    }
}