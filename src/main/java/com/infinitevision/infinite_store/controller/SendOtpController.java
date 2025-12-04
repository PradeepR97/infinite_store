package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.SendOtpRequest;
import com.infinitevision.infinite_store.service.OtpService;
import com.infinitevision.infinite_store.exception.OtpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/otp")
public class SendOtpController {

    @Autowired
    private OtpService otpService;

    @PostMapping("/send")
    public ApiResponse<String> sendOtp(@RequestBody SendOtpRequest request) {

        String phone = request.getPhoneNumber();


        if (phone == null || phone.length() != 10 || !phone.matches("\\d+")) {
            throw new OtpException("Phone number must be exactly 10 digits and contain only numbers");
        }


        String otp = otpService.generateOtp();  // static OTP = 123456

        return ApiResponse.success("OTP generated successfully", otp);
    }
}
