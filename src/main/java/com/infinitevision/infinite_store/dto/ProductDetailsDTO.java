package com.infinitevision.infinite_store.dto;

import lombok.*;

import java.util.List;
@Getter
@Setter
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDetailsDTO {

    private Long id;
    private String productName;
    private Double mrp;
    private Double price;
    private Double rating;
    private Integer totalReviews;
    private String unitType;

    private List<String> images;
    private List<String> highlights;
    private List<String> coupons;

    private DistributorDTO distributor;
}