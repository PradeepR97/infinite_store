package com.infinitevision.infinite_store.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

@Data

public class CreateUserRequestDTO {
    @NotBlank(message = "Name is required")

    @Pattern(
            regexp = "\\d{10}",
            message = "Phonenumber must be exactly 10 digits"
    )
    private String phoneNumber;
    @NotBlank(message = "Name is required")
    @Pattern(
            regexp = "^[A-Za-z ]+$",
            message = "Name must contain only letters"
    )
    private String name;
}

