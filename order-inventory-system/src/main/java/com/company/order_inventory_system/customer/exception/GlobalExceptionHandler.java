package com.company.order_inventory_system.customer.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/* Handles exceptions globally across the application */
@RestControllerAdvice

public class GlobalExceptionHandler {

    /* Handles customer not found exception */
    @ExceptionHandler(
            CustomerNotFoundException.class
    )

    public ResponseEntity<String>
    handleCustomerNotFoundException(
            CustomerNotFoundException exception) {

        /* Returns customer not found message with HTTP 404 status */
        return new ResponseEntity<>(
                exception.getMessage(),
                HttpStatus.NOT_FOUND
        );
    }
}