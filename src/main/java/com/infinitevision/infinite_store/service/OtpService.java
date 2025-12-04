package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.exception.OtpException;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    // static OTP for now
    private static final String STATIC_OTP = "123456";

    public static String sendOtp(String phoneNumber) {
        // always return static OTP for now
        return STATIC_OTP;
    }

    public String verifyOtp(String phoneNumber, String otp) {

        if (phoneNumber == null || !phoneNumber.matches("\\d{10}")) {
            throw new OtpException("Phone number must be exactly 10 digits and contain only numbers");
        }

        if (otp == null || otp.isEmpty()) {
            throw new OtpException("OTP is required");
        }

        if (!STATIC_OTP.equals(otp)) {
            throw new OtpException("Invalid OTP");
        }

        return JwtService.generateToken(phoneNumber);
    }

}
