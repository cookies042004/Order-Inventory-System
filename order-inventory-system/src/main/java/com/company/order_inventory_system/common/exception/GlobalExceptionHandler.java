package com.company.order_inventory_system.common.exception;

import com.company.order_inventory_system.customer.exception.CustomerNotFoundException;
import com.company.order_inventory_system.order.exception.OrderItemNotFoundException;
import com.company.order_inventory_system.order.exception.OrderNotFoundException;
import com.company.order_inventory_system.product.exception.ProductNotFoundException;
import com.company.order_inventory_system.shipment.exception.ShipmentNotFoundException;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.validation.FieldError;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Resource Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Resource Not Found",
                        exception.getMessage()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.NOT_FOUND
        );
    }

    // Handle Duplicate Resource Exception
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponse> handleDuplicateResourceException(
            DuplicateResourceException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.CONFLICT.value(),
                        "Duplicate Resource",
                        exception.getMessage()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.CONFLICT
        );
    }

    // Handle Invalid Data Exception
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponse> handleInvalidDataException(
            InvalidDataException exception) {

        ErrorResponse errorResponse = new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Invalid Data",
                        exception.getMessage()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle Order Not Found Exception
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
                HttpStatus.NOT_FOUND
        );
    }

    // Handle Order Item Not Found Exception
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
                HttpStatus.NOT_FOUND
        );
    }

    // Handle Shipment Not Found Exception
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
                HttpStatus.NOT_FOUND
        );
    }

    // Handle Customer Not Found Exception
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponse>
    handleCustomerNotFoundException(
            CustomerNotFoundException ex) {

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Customer Not Found",
                        ex.getMessage()
                );

        return new ResponseEntity<>(
                response,
                HttpStatus.NOT_FOUND
        );
    }

    // Handle Product Not Found Exception
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
                HttpStatus.NOT_FOUND
        );
    }

    // Handle Validation Exceptions
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>>
    handleValidationExceptions(
            MethodArgumentNotValidException ex) {

        Map<String, String> errors =
                new HashMap<>();

        ex.getBindingResult()
                .getAllErrors()
                .forEach(error -> {

                    String fieldName =
                            ((FieldError) error).getField();

                    String errorMessage =
                            error.getDefaultMessage();

                    errors.put(fieldName, errorMessage);
                });

        return new ResponseEntity<>(
                errors,
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse>
    handleGenericException(
            Exception exception) {

        ErrorResponse errorResponse =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.INTERNAL_SERVER_ERROR.value(),
                        "Internal Server Error",
                        exception.getMessage()
                );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
    @ExceptionHandler(
            MethodArgumentTypeMismatchException.class)

    public ResponseEntity<ErrorResponse>
    handleMethodArgumentTypeMismatchException(
            MethodArgumentTypeMismatchException ex) {

        String message =
                "Invalid value for parameter: "
                        + ex.getName();

        if (ex.getRequiredType() != null
                && ex.getRequiredType()
                .equals(LocalDateTime.class)) {

            message =
                    "Invalid date-time format. "
                            + "Use format: yyyy-MM-dd'T'HH:mm:ss";
        }

        ErrorResponse response =
                new ErrorResponse(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        "Bad Request",
                        message);

        return new ResponseEntity<>(
                response,
                HttpStatus.BAD_REQUEST);
    }
}