package com.infinitevision.infinite_store.repository;


import com.infinitevision.infinite_store.domain.model.enums.ProductImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductImageRepository extends JpaRepository<ProductImage, Long> {
    List<ProductImage> findByProductId(Long productId);
}
