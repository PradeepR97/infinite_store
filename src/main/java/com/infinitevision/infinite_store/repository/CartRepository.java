package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.Cart;
import com.infinitevision.infinite_store.domain.model.enums.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Long> {
    Optional<Cart> findByUser(User user);
}