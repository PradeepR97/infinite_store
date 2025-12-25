package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.Cart;
import com.infinitevision.infinite_store.domain.model.enums.CartItem;
import com.infinitevision.infinite_store.domain.model.enums.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {

    Optional<CartItem> findByCartAndProduct(Cart cart, Product product);

    List<CartItem> findByCart(Cart cart);
}

