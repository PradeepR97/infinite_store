package com.infinitevision.infinite_store.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SendOtpRequest {

    private String phoneNumber;
}
