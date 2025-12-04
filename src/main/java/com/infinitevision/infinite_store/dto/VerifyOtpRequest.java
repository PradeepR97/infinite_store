package com.infinitevision.infinite_store.dto;

import lombok.Data;

@Data
public class VerifyOtpRequest {
    private String phoneNumber;
    private String otp;
}

