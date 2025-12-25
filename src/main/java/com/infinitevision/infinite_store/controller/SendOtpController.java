package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.domain.model.enums.UserOtp;
import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.SendOtpRequest;
import com.infinitevision.infinite_store.exception.OtpException;
import com.infinitevision.infinite_store.service.OtpService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class SendOtpController {

    private final OtpService otpService;

    @PostMapping("/sendOtp")
    public ApiResponse<?> sendOtp(@RequestBody SendOtpRequest request) {

        log.info("Received SendOtp request for phone: {}", request.getPhoneNumber());

        String phoneNumber = request.getPhoneNumber();

        if (phoneNumber == null || !phoneNumber.matches("\\d+")) {
            log.error("Invalid phone number format: {}", phoneNumber);
            throw new OtpException("Phone number must contain only numbers");
        }

        if (phoneNumber.length() != 10) {
            log.error("Phone number must be 10 digits: {}", phoneNumber);
            throw new OtpException("Phone number must be exactly 10 digits");
        }

        log.debug("Phone number validation passed for: {}", phoneNumber);

        UserOtp userOtp = otpService.sendOtp(phoneNumber);

        log.info("OTP generated successfully for phone: {}", phoneNumber);

        return ApiResponse.success(
                "OTP sent successfully",
                java.util.Map.of(
                        "otp", userOtp.getOtp(),
                        "createdAt", userOtp.getCreatedAt()
                )
        );
    }

}
