package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.ProductDistributorInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductDistributorRepository extends JpaRepository<ProductDistributorInfo, Long> {
    ProductDistributorInfo findByProductId(Long productId);
}
