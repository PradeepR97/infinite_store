package com.infinitevision.infinite_store.dto;

import com.infinitevision.infinite_store.domain.model.enums.AddressType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressResponseDTO {

    private Long addressId;
    private Long userId;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String state;
    private String pincode;
    private AddressType addressType;
    private Boolean defaultAddress;
}
