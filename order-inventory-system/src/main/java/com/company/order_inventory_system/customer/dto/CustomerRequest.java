package com.company.order_inventory_system.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/* DTO used for customer create and update requests */
public class CustomerRequest {

    /* Customer email address */
    @NotBlank(message = "Customer email address cannot be empty")
    @Email(message = "Please enter a valid customer email address")
    private String emailAddress;

    /* Customer full name */
    @NotBlank(message = "Customer name cannot be empty")
    private String fullName;

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(
            String emailAddress) {

        this.emailAddress = emailAddress;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(
            String fullName) {

        this.fullName = fullName;
    }

    public CustomerRequest() {
    }
}