package com.company.order_inventory_system.customer.dto;

/* DTO used for customer response */
public class CustomerResponse {

    /* Customer ID */
    private Integer customerId;

    /* Customer email address */
    private String emailAddress;

    /* Customer full name */
    private String fullName;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(
            Integer customerId) {

        this.customerId = customerId;
    }

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

    public CustomerResponse() {
    }
}