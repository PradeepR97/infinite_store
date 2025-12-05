package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.SendOtpRequest;
import com.infinitevision.infinite_store.exception.OtpException;
import com.infinitevision.infinite_store.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/otp")
@RequiredArgsConstructor
public class SendOtpController {

    private final OtpService otpService;

    @PostMapping("/SendOtp")
    public ApiResponse<?> sendOtp(@RequestBody SendOtpRequest request) {

        String phoneNumber = request.getPhoneNumber();


        if (phoneNumber == null || !phoneNumber.matches("\\d+")) {
            throw new OtpException("Phone number must contain only numbers");
        }

        if (phoneNumber.length() != 10) {
            throw new OtpException("Phone number must be exactly 10 digits");
        }


        String otp = OtpService.sendOtp(phoneNumber);
        log.info("OTP sent successfully to phone number: {}", phoneNumber);
        return ApiResponse.success(
                "OTP sent successfully",
                java.util.Map.of("otp", otp) // show OTP for testing
        );
    }
}
