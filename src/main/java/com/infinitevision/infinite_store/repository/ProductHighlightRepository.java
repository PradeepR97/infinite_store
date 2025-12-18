package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.ProductHighlight;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductHighlightRepository extends JpaRepository<ProductHighlight, Long> {
    List<ProductHighlight> findByProductId(Long productId);
}

