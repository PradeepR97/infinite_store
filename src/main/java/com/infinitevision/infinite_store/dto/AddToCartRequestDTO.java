package com.infinitevision.infinite_store.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddToCartRequestDTO {

    @NotNull(message = "Product ID is required")
    private Long productId;
}

