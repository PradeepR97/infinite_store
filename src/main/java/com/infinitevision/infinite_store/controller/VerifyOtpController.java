package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.VerifyOtpRequest;
import com.infinitevision.infinite_store.dto.VerifyOtpResponse;
import com.infinitevision.infinite_store.exception.OtpException;
import com.infinitevision.infinite_store.service.OtpService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class VerifyOtpController {

    private final OtpService otpService; // Inject service

    @PostMapping("/verifyOtp")
    public ApiResponse<VerifyOtpResponse> verifyOtp(@RequestBody VerifyOtpRequest request) {

        log.info("Received OTP verification request for phone={}", request.getPhoneNumber());

        if (request.getPhoneNumber() == null || !request.getPhoneNumber().matches("\\d{10}")) {
            log.error("Invalid phone number received: {}", request.getPhoneNumber());
            throw new OtpException("Invalid phone number");
        }

        if (request.getOtp() == null || request.getOtp().isEmpty()) {
            log.error("OTP missing for phone={}", request.getPhoneNumber());
            throw new OtpException("OTP is required");
        }

        log.debug("OTP validation passed for phone={}", request.getPhoneNumber());

        // Call service without name
        VerifyOtpResponse response =
                otpService.verifyOtp(request.getPhoneNumber(), request.getOtp());

        log.info(
                "OTP verified successfully for phone={}, userId={}, isNewUser={}",
                request.getPhoneNumber(),
                response.getUserId(),
                response.isNewUser()
        );

        return ApiResponse.success("OTP verified successfully", response);
    }
}
