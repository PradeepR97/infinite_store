package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.VerifyOtpRequest;
import com.infinitevision.infinite_store.service.OtpService;
import com.infinitevision.infinite_store.exception.OtpException;

import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
public class VerifyOtpController {

    private final OtpService otpService;

    public VerifyOtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/verifyOtp")
    public ApiResponse<?> verifyOtp(@RequestBody VerifyOtpRequest request) {

        log.info("Received VerifyOtp request for phone: {}", request.getPhoneNumber());

        String phoneNumber = request.getPhoneNumber();
        String otp = request.getOtp();

        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            log.error("Invalid phone number format: {}", phoneNumber);
            throw new OtpException("Phone number must contain only numbers and be 10 digits");
        }

        if (otp == null || otp.isEmpty()) {
            log.error("OTP is null or empty for phone: {}", phoneNumber);
            throw new OtpException("OTP is required");
        }

        log.debug("Phone number and OTP validation passed for: {}", phoneNumber);

        Object result = otpService.verifyOtp(phoneNumber, otp);

        log.info("OTP verification completed for phone: {}", phoneNumber);

        if (result instanceof Long) {
            log.info("User ID returned after OTP verification: {}", result);
            return ApiResponse.success(
                    "OTP verified successfully",
                    java.util.Map.of("userId", result)
            );

        } else if (result instanceof String) {
            log.info("Token returned after OTP verification for phone: {}", phoneNumber);
            return ApiResponse.success(
                    "OTP verified successfully",
                    java.util.Map.of("token", result)
            );

        } else {
            log.error("Unexpected result type during OTP verification: {}", result);
            throw new OtpException("Unexpected error");
        }
    }

}
