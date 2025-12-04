package com.infinitevision.infinite_store.exception;

import com.infinitevision.infinite_store.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleAllExceptions(Exception ex) {

        logger.error("Unhandled exception occurred: {}", ex.getMessage(), ex);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponse.failure("Internal server error. Please try again later", 500));
    }

    @ExceptionHandler(OtpException.class)
    public ResponseEntity<ApiResponse<Object>> handleOtpException(OtpException ex) {

        logger.error("OTP error: {}", ex.getMessage());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ApiResponse.failure(ex.getMessage(), 400));
    }
}