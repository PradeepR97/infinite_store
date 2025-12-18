package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.*;
import com.infinitevision.infinite_store.dto.*;
import com.infinitevision.infinite_store.repository.*;
import com.infinitevision.infinite_store.security.JwtService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AddressService {

    private final AddressRepository addressRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public AddressResponseDTO addAddress(String token, AddAddressRequestDTO dto) {

        Long userId = jwtService.extractUserId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("Adding {} address for user {}", dto.getAddressType(), userId);

        // 🚫 LIMIT: ONLY ONE HOME & ONE WORK PER USER
        if (dto.getAddressType() == AddressType.HOME ||
                dto.getAddressType() == AddressType.WORK) {

            boolean exists = addressRepository
                    .existsByUserAndAddressType(user, dto.getAddressType());

            if (exists) {
                throw new RuntimeException(
                        "Only one " + dto.getAddressType() + " address is allowed per user"
                );
            }
        }

        // ⭐ DEFAULT ADDRESS LOGIC
        boolean hasAnyAddress = addressRepository.existsByUser(user);

        Address address = Address.builder()
                .user(user)
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .city(dto.getCity())
                .state(dto.getState())
                .pincode(dto.getPincode())
                .addressType(dto.getAddressType())
                .defaultAddress(!hasAnyAddress) // first address = default
                .build();

        addressRepository.save(address);

        log.info("Address saved successfully for user {}", userId);

        return mapToResponse(address);
    }

    public List<AddressResponseDTO> getAddresses(String token) {

        Long userId = jwtService.extractUserId(token);
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        log.info("Fetching addresses for user {}", userId);

        return addressRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private AddressResponseDTO mapToResponse(Address address) {
        return new AddressResponseDTO(
                address.getId(),
                address.getUser().getId(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getCity(),
                address.getState(),
                address.getPincode(),
                address.getAddressType(),
                address.getDefaultAddress()
        );
    }
}
