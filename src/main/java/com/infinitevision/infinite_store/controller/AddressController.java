package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.*;
import com.infinitevision.infinite_store.service.AddressService;
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

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<AddressResponseDTO>> addAddress(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @Valid @RequestBody AddAddressRequestDTO request) {

        log.info("POST /api/address/add called");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.failure("Authentication is required", 401));
        }

        String token = authHeader.substring(7);

        AddressResponseDTO response = addressService.addAddress(token, request);

        return ResponseEntity.ok(
                ApiResponse.success("Address added successfully", response)
        );
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<AddressResponseDTO>>> getAddresses(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        log.info("GET /api/address called");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.failure("Authentication is required", 401));
        }

        String token = authHeader.substring(7);

        return ResponseEntity.ok(
                ApiResponse.success(
                        "Addresses fetched successfully",
                        addressService.getAddresses(token)
                )
        );
    }
}
