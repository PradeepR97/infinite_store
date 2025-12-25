package com.infinitevision.infinite_store.repository;

import com.infinitevision.infinite_store.domain.model.enums.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

    List<OrderItem> findByOrderId(String orderId);

}

