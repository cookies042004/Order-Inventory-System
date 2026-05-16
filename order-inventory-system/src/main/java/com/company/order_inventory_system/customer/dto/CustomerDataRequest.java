package com.company.order_inventory_system.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/* DTO class for customer request and response */
public class CustomerDataRequest {

    /* Customer email address */
    @NotBlank(message = "Customer email address cannot be empty")
    @Email(message = "Please enter a valid customer email address")
    private String emailAddress;

    /* Customer full name */
    @NotNull(message = "Customer name cannot be null")
    private String fullName;

    /* Getter for emailAddress */
    public String getEmailAddress() {
        return emailAddress;
    }

    /* Setter for emailAddress */
    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    /* Getter for fullName */
    public String getFullName() {
        return fullName;
    }

    /* Setter for fullName */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /* Parameterized constructor */
    public CustomerDataRequest(String emailAddress,
                               String fullName) {

        this.emailAddress = emailAddress;
        this.fullName = fullName;
    }

    /* Default constructor */
    public CustomerDataRequest() {
    }
}