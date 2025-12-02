package com.infinitevision.infinite_store.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {

    private String message;
    private int statusCode;
    private boolean success;
    private T data;

    public static <T> ApiResponse<T> success(String message, T data) {
        return new ApiResponse<>(message, 200, true, data);
    }

    public static <T> ApiResponse<T> failure(String message, int statusCode) {
        return new ApiResponse<>(message, statusCode, false, null);
    }
}

