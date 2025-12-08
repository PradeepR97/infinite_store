package com.infinitevision.infinite_store.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LocationResponse {
    private String ip;
    private String city;
    private String region;
    private String country;
    private Double latitude;
    private Double longitude;
    private String homeNumber;
    private String street;
    private String landmark;
    private String pincode;
    private String phoneNumber;
    private String address; // Full formatted address
}