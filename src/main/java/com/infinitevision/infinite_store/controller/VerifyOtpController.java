package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.VerifyOtpRequest;
import com.infinitevision.infinite_store.service.JwtService;
import com.infinitevision.infinite_store.service.OtpService;
import com.infinitevision.infinite_store.exception.OtpException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/otp")
public class VerifyOtpController {

    private final OtpService otpService;

    public VerifyOtpController(OtpService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/verify-Otp")
    public ApiResponse<?> verifyOtp(@RequestBody VerifyOtpRequest request) {

        String phoneNumber = request.getPhoneNumber();
        String otp = request.getOtp();

        if (phoneNumber == null || !phoneNumber.matches("\\d+")) {
            throw new OtpException("Phone number must contain only numbers");
        }

        if (phoneNumber.length() != 10) {
            throw new OtpException("Phone number must be exactly 10 digits");
        }

        if (otp == null || otp.isEmpty()) {
            throw new OtpException("OTP is required");
        }

        // Service now returns token
        String token = otpService.verifyOtp(phoneNumber, otp);
        log.info("OTP verification successful for phone number: {}", phoneNumber);
        return ApiResponse.success(
                "OTP verified successfully",
                java.util.Map.of("token", token)
        );
    }


}