package com.infinitevision.infinite_store.dto;

import com.infinitevision.infinite_store.domain.model.enums.AddressType;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class AddAddressRequestDTO {

    @NotBlank
    private String addressLine1;

    private String addressLine2;

    @NotBlank
    private String city;

    @NotBlank
    private String state;

    @Pattern(regexp = "\\d{6}", message = "Pincode must be 6 digits")
    private String pincode;

    @NotNull
    private AddressType addressType;
}
