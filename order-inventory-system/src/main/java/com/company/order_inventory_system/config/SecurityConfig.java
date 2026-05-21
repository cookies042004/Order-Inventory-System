package com.company.order_inventory_system.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;

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
   ORDER ITEM CREDENTIALS
   ========================= */

    @Value("${orderitem.username}")
    private String orderItemUsername;

    @Value("${orderitem}")
    private String orderItemPassword;


    /* =========================
       SECURITY FILTER CHAIN
       ========================= */

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http

                .csrf(csrf -> csrf.disable())

                .authorizeHttpRequests(auth -> auth

                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html"
                        )
                        .permitAll()

                        .requestMatchers("/api/customers/**")
                        .hasRole("CUSTOMER")

                        .requestMatchers("/api/products/**")
                        .hasRole("PRODUCT")

                        .requestMatchers("/api/orders/**")
                        .hasRole("ORDER")

                        .requestMatchers("/api/order-items/**")
                        .hasRole("ORDERITEM")

                        .requestMatchers("/api/shipments/**")
                        .hasRole("SHIPMENT")

                        .requestMatchers("/api/inventories/**")
                        .hasRole("INVENTORY")

                        .requestMatchers("/api/stores/**")
                        .hasRole("STORE")

                        .requestMatchers("/api/reports/**")
                        .hasRole("REPORT")

                        .anyRequest()
                        .authenticated()
                )

                .httpBasic(Customizer.withDefaults())

                .exceptionHandling(ex -> ex
                        .authenticationEntryPoint(
                                new BasicAuthenticationEntryPoint() {{
                                    setRealmName("OrderInventorySystem");
                                    afterPropertiesSet();
                                }}
                        )
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

        /* ORDER ITEM USER */

        UserDetails orderItemUser =

                User.builder()

                        .username(orderItemUsername)

                        .password(
                                passwordEncoder()
                                        .encode(orderItemPassword)
                        )

                        .roles("ORDERITEM")

                        .build();


        return new InMemoryUserDetailsManager(

                customerUser,

                productUser,

                orderUser,

                shipmentUser,

                inventoryUser,

                storeUser,

                reportUser,

                orderItemUser
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