package com.company.order_inventory_system.common.exception;

import com.company.order_inventory_system.order.exception.OrderItemNotFoundException;
import com.company.order_inventory_system.order.exception.OrderNotFoundException;
import com.company.order_inventory_system.shipment.exception.ShipmentNotFoundException;
import com.company.order_inventory_system.product.exception.ProductNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice

public class GlobalExceptionHandler {

    @ExceptionHandler(OrderNotFoundException.class)

    public ResponseEntity<ErrorResponse>
    handleOrderNotFoundException(
            OrderNotFoundException ex) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Order Not Found",
                        ex.getMessage()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(OrderItemNotFoundException.class)

    public ResponseEntity<ErrorResponse>
    handleOrderItemNotFoundException(
            OrderItemNotFoundException ex) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Order Item Not Found",
                        ex.getMessage()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ShipmentNotFoundException.class)

    public ResponseEntity<ErrorResponse>
    handleShipmentNotFoundException(
            ShipmentNotFoundException ex) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Shipment Not Found",
                        ex.getMessage()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProductNotFoundException.class)

    public ResponseEntity<ErrorResponse>
    handleProductNotFoundException(
            ProductNotFoundException ex) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Product Not Found",
                        ex.getMessage()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(
            MethodArgumentNotValidException.class)

    public ResponseEntity<Map<String, String>>
    handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {

                    String fieldName =
                            ((FieldError) error)
                                    .getField();

                    String errorMessage =
                            error.getDefaultMessage();

                    errors.put(
                            fieldName,
                            errorMessage);
                });

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)

    public ResponseEntity<ErrorResponse>
    handleGenericException(
            Exception ex) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        ex.getMessage()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}