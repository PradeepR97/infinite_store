package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.UserOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserOtpRepository extends JpaRepository<UserOtp, Long> {

    // Get the latest OTP for a phone number
    Optional<UserOtp> findFirstByPhoneNumberOrderByCreatedAtDesc(String phoneNumber);
}
