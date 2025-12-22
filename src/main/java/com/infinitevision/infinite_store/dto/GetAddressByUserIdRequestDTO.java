package com.infinitevision.infinite_store.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class GetAddressByUserIdRequestDTO {

    @NotNull
    private Long userId;
}


