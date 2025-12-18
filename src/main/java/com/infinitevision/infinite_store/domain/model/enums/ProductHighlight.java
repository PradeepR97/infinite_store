package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "product_highlights")
public class ProductHighlight {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_id")
    private Long productId;

    @Column(name = "highlight_text")
    private String highlightText;
}

