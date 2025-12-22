package com.infinitevision.infinite_store.exception;

import com.infinitevision.infinite_store.dto.ApiResponse;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger =
            LoggerFactory.getLogger(GlobalExceptionHandler.class);

    // ✅ 1. Address validation errors
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Object>> handleValidationException(
            MethodArgumentNotValidException ex) {

        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .findFirst()
                .map(error -> error.getDefaultMessage())
                .orElse("Invalid address data");

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(errorMessage, 400));
    }

    // ✅ 2. Path / param validation
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<ApiResponse<Object>> handleConstraintViolation(
            ConstraintViolationException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage(), 400));
    }

    // ✅ 3. Business validation
    @ExceptionHandler(HomeAddressAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Object>> handleHomeAddressExists(
            HomeAddressAlreadyExistsException ex) {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage(), 400));
    }

    // ✅ 4. Resource not found
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ApiResponse<Object>> handleRuntimeException(
            RuntimeException ex) {

        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ApiResponse.failure(ex.getMessage(), 404));
    }

    // ✅ 5. Fallback
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure(
                        "Internal server error. Please try again later",
                        500
                ));
    }
}
