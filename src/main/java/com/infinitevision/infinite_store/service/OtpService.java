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
    private final JwtService jwtService;   // ✅ inject JwtService

    private static final String STATIC_OTP = "123456";

    // Step 1: Send OTP
    public UserOtp sendOtp(String phoneNumber) {

        UserOtp userOtp = new UserOtp();
        userOtp.setPhoneNumber(phoneNumber);
        userOtp.setOtp(STATIC_OTP);
        userOtp.setCreatedAt(LocalDateTime.now());
        userOtp.setCreatedBy("SYSTEM");

        return userOtpRepository.save(userOtp);
    }

    // Step 2: Verify OTP & Generate Token
    public String verifyOtp(String phoneNumber, String otp) {

        UserOtp dbOtp = userOtpRepository
                .findFirstByPhoneNumberOrderByCreatedAtDesc(phoneNumber)
                .orElseThrow(() -> new OtpException("OTP not found"));

        if (!dbOtp.getOtp().equals(otp)) {
            throw new OtpException("Invalid OTP");
        }

        // ✅ Fetch or create user
        User user = userRepository.findByPhoneNumber(phoneNumber)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setPhoneNumber(phoneNumber);
                    return userRepository.save(newUser);
                });

        // ✅ Generate token with userId + phone
        return jwtService.generateToken(user.getId(), phoneNumber);
    }
}
