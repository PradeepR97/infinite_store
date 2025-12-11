package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "products")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseUser {

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

    // 🔥 Auto calculate price using MRP + Discount
    @PrePersist
    @PreUpdate
    public void calculatePriceFromDiscount() {
        if (mrp != null && discountPercentage != null) {
            this.price = mrp - ((mrp * discountPercentage) / 100);
        }
    }
}


