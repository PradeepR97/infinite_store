package com.infinitevision.infinite_store.dto;

public class SendOtpResponse {

    private String otp;
    private String message;

    public SendOtpResponse(String otp, String message) {
        this.otp = otp;
        this.message = message;
    }

    public String getOtp() {
        return otp;
    }

    public String getMessage() {
        return message;
    }
}

