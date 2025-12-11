package com.infinitevision.infinite_store.dto;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDTO {

    private Long id;
    private String productName;
    private String description;
    private Double price;
    private Double mrp;
    private Double discountPercentage;
    private Long categoryId;
    private Long subcategoryId;
    private String brand;
    private Double weight;
    private String unitType;
    private String imageUrl;
    private String thumbnailUrl;
    private Integer stock;
    private Boolean isAvailable;
    private Double rating;
    private Integer totalReviews;
}

