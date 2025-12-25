package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.User;
import com.infinitevision.infinite_store.domain.model.enums.UserOtp;
import com.infinitevision.infinite_store.dto.VerifyOtpResponse;
import com.infinitevision.infinite_store.exception.OtpException;
import com.infinitevision.infinite_store.repository.UserOtpRepository;
import com.infinitevision.infinite_store.repository.UserRepository;
import com.infinitevision.infinite_store.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class OtpService {
    private final UserRepository userRepository;
    private final UserOtpRepository userOtpRepository;
    private final JwtService jwtService;

    private static final String STATIC_OTP = "123456";

    // ---------------- SEND OTP ----------------
    public UserOtp sendOtp(String phoneNumber) {

        log.info("Sending OTP to phone={}", phoneNumber);

        UserOtp userOtp = new UserOtp();
        userOtp.setPhoneNumber(phoneNumber);
        userOtp.setOtp(STATIC_OTP);
        userOtp.setCreatedAt(LocalDateTime.now());
        userOtp.setCreatedBy("SYSTEM");

        UserOtp savedOtp = userOtpRepository.save(userOtp);

        log.info("OTP saved successfully for phone={}", phoneNumber);

        return savedOtp;
    }

    public VerifyOtpResponse verifyOtp(String phoneNumber, String otp) {
        UserOtp dbOtp = userOtpRepository
                .findTopByPhoneNumberOrderByCreatedAtDesc(phoneNumber)
                .orElseThrow(() -> new OtpException("OTP not found"));

        if (!dbOtp.getOtp().equals(otp)) {
            throw new OtpException("Invalid OTP");
        }

        Optional<User> optionalUser = userRepository.findByPhoneNumber(phoneNumber);

        Long userId = null;
        boolean isNewUser = true;
        String token;

        if (optionalUser.isPresent()) {
            // Already registered → generate FULL JWT
            userId = optionalUser.get().getId();
            isNewUser = false;
            token = jwtService.generateToken(userId, phoneNumber);
        } else {
            // New user → generate TEMP token (only phone)
            token = jwtService.generateTempToken(phoneNumber);
        }

        return new VerifyOtpResponse(userId, isNewUser, token);
    }

}
