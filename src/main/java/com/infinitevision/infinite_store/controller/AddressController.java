package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.*;
import com.infinitevision.infinite_store.service.AddressService;
import com.infinitevision.infinite_store.security.JwtService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;
    private final JwtService jwtService;


    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AddressResponseDTO>> addAddress(
            @RequestHeader(value = "Authorization", required = true) String authHeader,
            @Valid @RequestBody AddAddressRequestDTO request) {

        log.info("POST /api/address/add called");

        if (!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.failure("Invalid Authorization header", 401));
        }

        String token = authHeader.substring(7);


        Long tokenUserId = jwtService.extractUserId(token);


        if (request.getUserId() != null && !tokenUserId.equals(request.getUserId())) {
            return ResponseEntity.status(403)
                    .body(ApiResponse.failure("Unauthorized access", 403));
        }

        AddressResponseDTO response = addressService.addAddress(token, request);

        return ResponseEntity.ok(ApiResponse.success("Address added successfully", response));
    }

    @PostMapping("")
    public ResponseEntity<ApiResponse<List<AddressResponseDTO>>> getAddressByUserId(
            @RequestHeader(value = "Authorization", required = true) String authHeader,
            @Valid @RequestBody GetAddressByUserIdRequestDTO request) {

        log.info("POST /api/address/by-user-id called for userId={}", request.getUserId());

        if (!authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.failure("Invalid Authorization header", 401));
        }

        String token = authHeader.substring(7);

        // Extract userId from token
        Long tokenUserId = jwtService.extractUserId(token);

        // Authorization check: token userId must match requested userId
        if (!tokenUserId.equals(request.getUserId())) {
            return ResponseEntity.status(403)
                    .body(ApiResponse.failure("Unauthorized access", 403));
        }

        // Authorized, fetch addresses
        List<AddressResponseDTO> addresses = addressService.getAddressesByUserId(token, request.getUserId());

        return ResponseEntity.ok(
                ApiResponse.success("Addresses fetched successfully", addresses)
        );
    }
}
