package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.CreateUserRequestDTO;
import com.infinitevision.infinite_store.domain.model.enums.User;
import com.infinitevision.infinite_store.service.UserService;
import com.infinitevision.infinite_store.security.JwtService;
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
            @RequestBody CreateUserRequestDTO dto) {

        if (authorization == null || authorization.isBlank()) {
            throw new RuntimeException("Authorization header required");
        }

        // Remove "Bearer " if present
        String token = authorization.startsWith("Bearer ") ? authorization.substring(7) : authorization;

        // Extract phone from temp token
        String phone = jwtService.extractPhoneFromTempToken(token);

        Long userId = userService.createUserFromPhone(phone, dto);

        // Generate FULL JWT now
        String fullToken = jwtService.generateToken(userId, phone);

        return ApiResponse.success(
                "User created successfully",
                Map.of("userId", userId, "token", fullToken)
        );
    }

}
