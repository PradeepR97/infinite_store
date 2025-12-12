package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.User;
import com.infinitevision.infinite_store.domain.model.enums.UserOtp;
import com.infinitevision.infinite_store.exception.OtpException;
import com.infinitevision.infinite_store.repository.UserOtpRepository;
import com.infinitevision.infinite_store.repository.UserRepository;
import com.infinitevision.infinite_store.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class OtpService {

    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;

    private static final String STATIC_OTP = "123456";

    // Step 1: Send OTP
    public UserOtp sendOtp(String phoneNumber) {

        String otp = STATIC_OTP; // static OTP for testing

        UserOtp userOtp = new UserOtp();
        userOtp.setPhoneNumber(phoneNumber);
        userOtp.setOtp(otp);
        userOtp.setCreatedAt(LocalDateTime.now());
        userOtp.setCreatedBy("NULL");

        return userOtpRepository.save(userOtp);
    }


    public Object verifyOtp(String phoneNumber, String otp) {


        UserOtp dbOtp = userOtpRepository.findFirstByPhoneNumberOrderByCreatedAtDesc(phoneNumber)
                .orElseThrow(() -> new OtpException("OTP not found"));

        if (!dbOtp.getOtp().equals(otp)) {
            throw new OtpException("Invalid OTP");
        }


        User user = userRepository.findByPhoneNumber(phoneNumber).orElse(null);

        if (user != null) {

            return user.getId();
        } else {

            User newUser = new User();
            newUser.setPhoneNumber(phoneNumber);
            userRepository.save(newUser);

            String token = JwtService.generateToken(phoneNumber);
            return token;
        }
    }
}
