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

        String phoneNumber = request.getPhoneNumber();
        String otp = request.getOtp();

        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new OtpException("Phone number must contain only numbers and be 10 digits");
        }

        if (otp == null || otp.isEmpty()) {
            throw new OtpException("OTP is required");
        }

        Object result = otpService.verifyOtp(phoneNumber, otp);

        if (result instanceof Long) {

            return ApiResponse.success(
                    "OTP verified successfully",
                    java.util.Map.of("userId", result)
            );
        } else if (result instanceof String) {

            return ApiResponse.success(
                    "OTP verified successfully",
                    java.util.Map.of("token", result)
            );
        } else {
            throw new OtpException("Unexpected error");
        }
    }



}