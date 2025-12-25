package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

