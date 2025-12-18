package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.ProductCoupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductCouponRepository extends JpaRepository<ProductCoupon, Long> {
    List<ProductCoupon> findByProductId(Long productId);
}
