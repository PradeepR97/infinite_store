package com.infinitevision.infinite_store.dto;



import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ManualLocationRequest {
    private String homeNumber;
    private String street;
    private String landmark;
    private String pincode;
    private String phoneNumber;
}

