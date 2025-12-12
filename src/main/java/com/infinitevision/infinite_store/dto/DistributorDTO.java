package com.infinitevision.infinite_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DistributorDTO {
    private String sellerName;
    private String sellerAddress;
    private String licenseNo;
    private String countryOfOrigin;
}
