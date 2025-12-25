package com.infinitevision.infinite_store.dto;



import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VerifyOtpResponse {
    private Long userId;
    private boolean isNewUser;
    private String token;

}

