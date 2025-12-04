package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.exception.OtpException;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

    public String generateOtp() {
        return "123456";
    }


    public String sendOtp(String phoneNumber) {

        if (phoneNumber == null || !phoneNumber.matches("\\d+")) {
            throw new OtpException("Phone number must contain only numbers");
        }

        String otp = generateOtp();

        return otp;
    }
}
