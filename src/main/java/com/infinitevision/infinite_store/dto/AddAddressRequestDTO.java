package com.infinitevision.infinite_store.dto;

import com.infinitevision.infinite_store.domain.model.enums.AddressType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddAddressRequestDTO {

    @NotBlank
    private String addressLine1;   // ✅ MUST exist
    @NotBlank
    private String landmark;       // ✅ new field
    @NotBlank
    private String addressLine2;
    private Long userId;


    @NotBlank(message = "City is required")
    private String city;

    @NotBlank(message = "State is required")
    private String state;



    @Pattern(
            regexp = "\\d{6}",
            message = "Pincode must be exactly 6 digits"
    )
    private String pincode;

    @NotNull(message = "Address type is required")
    private AddressType addressType;
}

