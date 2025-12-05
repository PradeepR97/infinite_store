package com.infinitevision.infinite_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SendOtpResponse {

    private String otp;
    private String message;
}
