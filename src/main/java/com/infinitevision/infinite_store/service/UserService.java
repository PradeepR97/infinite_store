package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.User;
import com.infinitevision.infinite_store.dto.CreateUserRequestDTO;
import com.infinitevision.infinite_store.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    /**
     * Create a new user after OTP verification
     */
    public Long createUserFromPhone(String phoneNumber, CreateUserRequestDTO dto) {

        // Check if user already exists
        User existingUser = userRepository.findByPhoneNumber(phoneNumber).orElse(null);
        if (existingUser != null) {
            // You can update existing user info if needed
            existingUser.setName(dto.getName());
            existingUser.setUpdatedAt(LocalDateTime.now());
            userRepository.save(existingUser);
            return existingUser.getId();
        }

        // Create new user
        User newUser = new User();
        newUser.setPhoneNumber(phoneNumber);
        newUser.setName(dto.getName());
        newUser.setOnboardingCompleted(false);
        newUser.setCreatedAt(LocalDateTime.now());
        newUser.setCreatedBy("SYSTEM");

        User savedUser = userRepository.save(newUser);
        return savedUser.getId();
    }
}
