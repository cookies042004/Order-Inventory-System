package com.company.order_inventory_system.common.exception;

import com.company.order_inventory_system.store.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Handle Resource Not Found Exception
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleResourceNotFoundException(ResourceNotFoundException exception) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                        false,
                        exception.getMessage(),
                        LocalDateTime.now()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.NOT_FOUND
        );
    }

    // Handle Duplicate Resource Exception
    @ExceptionHandler(DuplicateResourceException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleDuplicateResourceException(DuplicateResourceException exception) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                        false,
                        exception.getMessage(),
                        LocalDateTime.now()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.CONFLICT
        );
    }

    // Handle Invalid Data Exception
    @ExceptionHandler(InvalidDataException.class)
    public ResponseEntity<ErrorResponseDTO>
    handleInvalidDataException(InvalidDataException exception) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                        false,
                        exception.getMessage(),
                        LocalDateTime.now()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.BAD_REQUEST
        );
    }

    // Handle Generic Exceptions
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO>
    handleGenericException(Exception exception) {

        ErrorResponseDTO errorResponse = new ErrorResponseDTO(
                        false,
                        exception.getMessage(),
                        LocalDateTime.now()
        );

        return new ResponseEntity<>(
                errorResponse,
                HttpStatus.INTERNAL_SERVER_ERROR
        );
    }
}