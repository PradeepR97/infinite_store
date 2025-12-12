package com.infinitevision.infinite_store.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductCardDTO {

    private Long id;
    private String productName;
    private Double mrp;
    private Double price;
    private String thumbnailUrl;
    private Double rating;
    private String unitType;
    private Double discountPercentage;
    private Integer totalReviews;
}


