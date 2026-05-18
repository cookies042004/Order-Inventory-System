package com.company.order_inventory_system.customer.entity;

import jakarta.persistence.*;

import java.util.Objects;

/* Entity class for customers table */
@Entity

/* Maps with customers table */
@Table(name = "customers")

public class Customer {

    /* Primary key */
    @Id

    /* Auto increments ID */
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    /* Maps customer_id column */
    @Column(name = "customer_id")
    private Integer customerId;

    /* Maps email_address column */
    @Column(
            name = "email_address"
    )

    private String emailAddress;

    /* Maps full_name column */
    @Column(
            name = "full_name"
    )

    private String fullName;

    /* Getter for customerId */
    public Integer getCustomerId() {
        return customerId;
    }

    /* Setter for customerId */
    public void setCustomerId(
            Integer customerId) {

        this.customerId = customerId;
    }

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

    /* Parameterized constructor */
    public Customer(
            Integer customerId,
            String emailAddress,
            String fullName) {

        this.customerId = customerId;
        this.emailAddress = emailAddress;
        this.fullName = fullName;
    }

    /* Default constructor */
    public Customer() {
    }

    /* Equals method for object comparison */
    @Override
    public boolean equals(Object object) {

        if (this == object) {
            return true;
        }

        if (object == null ||
                getClass() != object.getClass()) {

            return false;
        }

        Customer customer =
                (Customer) object;

        return Objects.equals(
                customerId,
                customer.customerId
        );
    }

    /* Hashcode method */
    @Override
    public int hashCode() {

        return Objects.hash(customerId);
    }
}