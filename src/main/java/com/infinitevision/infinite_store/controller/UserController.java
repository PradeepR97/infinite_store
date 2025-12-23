package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.CreateUserRequestDTO;
import com.infinitevision.infinite_store.service.UserService;
import com.infinitevision.infinite_store.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final JwtService jwtService;

    @PostMapping("/create")
    public ApiResponse<?> createUser(

            @RequestHeader("Authorization") String authorization,
    @Valid @RequestBody CreateUserRequestDTO dto)
    {

        log.info("POST /api/user/create called");

        if (authorization == null || authorization.isBlank()) {
            log.warn("Authorization header missing");
            throw new RuntimeException("Authorization header required");
        }

        // Remove "Bearer " if present
        String token = authorization.startsWith("Bearer ") ? authorization.substring(7) : authorization;
        log.info("Extracting phone from temporary token");

        // Extract phone from temp token
        String phone = jwtService.extractPhoneFromTempToken(token);
        log.info("Phone number extracted from token: {}", phone);

        log.info("Creating user for phone: {}", phone);
        Long userId = userService.createUserFromPhone(phone, dto);
        log.info("User created successfully with userId={}", userId);

        // Generate full JWT now
        String fullToken = jwtService.generateToken(userId, phone);
        log.info("Full JWT generated for userId={}", userId);

        return ApiResponse.success(
                "User created successfully",
                Map.of("userId", userId, "token", fullToken)
        );
    }
}
