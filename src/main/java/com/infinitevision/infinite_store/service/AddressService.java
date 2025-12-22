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


        boolean hasAnyAddress = addressRepository.existsByUser(user);

        Address address = Address.builder()
                .user(user)
                .addressLine1(dto.getAddressLine1())
                .addressLine2(dto.getAddressLine2())
                .landmark(dto.getLandmark())
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

    public List<AddressResponseDTO> getAddressesByUserId(String token, Long userId) {
        log.info("Fetching addresses for userId={}", userId);

        Long tokenUserId = jwtService.extractUserId(token);
        log.info("Extracted userId={} from token", tokenUserId);

        // Authorization check
        if (!tokenUserId.equals(userId)) {
            log.warn("Unauthorized access attempt. Token userId={} does not match requested userId={}", tokenUserId, userId);
            throw new RuntimeException("Unauthorized access");
        }

        // Fetch user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> {
                    log.error("User not found with userId={}", userId);
                    return new RuntimeException("User not found");
                });
        log.info("User found: {}", user.getId());

        // Fetch addresses
        List<AddressResponseDTO> addresses = addressRepository.findByUser(user)
                .stream()
                .map(this::mapToResponse)
                .toList();

        log.info("Found {} addresses for userId={}", addresses.size(), userId);

        return addresses;
    }




    private AddressResponseDTO mapToResponse(Address address) {
        return new AddressResponseDTO(
                address.getId(),
                address.getUser().getId(),
                address.getAddressLine1(),
                address.getAddressLine2(),
                address.getLandmark(),
                address.getCity(),
                address.getState(),
                address.getPincode(),
                address.getAddressType(),
                address.getDefaultAddress()
        );
    }
}
