package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
@Entity
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderId;

    private Long productId; // ✅ Only ID

    private Integer quantity;
    private Double price;
}


