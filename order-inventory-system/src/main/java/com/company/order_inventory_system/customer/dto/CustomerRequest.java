package com.company.order_inventory_system.customer.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

/* DTO used for customer create and update requests */
public class CustomerRequest {

    /* Customer email address */
    @NotBlank(
            message =
                    "Customer email address cannot be empty"
    )

    @Email(
            message =
                    "Please enter a valid customer email address"
    )

    @Size(
            max = 255,
            message =
                    "Email address cannot exceed 255 characters"
    )

    private String emailAddress;

    /* Customer full name */
    @NotBlank(
            message =
                    "Customer name cannot be empty"
    )

    @Pattern(
            regexp = "^[A-Za-z '-]+$",
            message =
                    "Customer name must contain only alphabets"
    )

    @Size(
            max = 255,
            message =
                    "Customer name cannot exceed 255 characters"
    )

    private String fullName;

    /* Getter for emailAddress */
    public String getEmailAddress() {
        return emailAddress;
    }

    /* Setter for emailAddress */
    public void setEmailAddress(
            String emailAddress) {

        this.emailAddress = emailAddress;
    }

    /* Getter for fullName */
    public String getFullName() {
        return fullName;
    }

    /* Setter for fullName */
    public void setFullName(
            String fullName) {

        this.fullName = fullName;
    }

    /* Default constructor */
    public CustomerRequest() {
    }
}