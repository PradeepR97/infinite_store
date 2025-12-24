package com.infinitevision.infinite_store.util;

import com.infinitevision.infinite_store.domain.model.enums.OrderStatus;

import java.time.Duration;
import java.time.LocalDateTime;

public class OrderStatusCalculator {

    private OrderStatusCalculator() {}

    public static OrderStatus calculate(LocalDateTime createdAt) {

        long seconds = Duration.between(createdAt, LocalDateTime.now()).getSeconds();

        if (seconds < 30) return OrderStatus.PACKING;
        if (seconds < 60) return OrderStatus.DELIVERY_PARTNER_PICKED;
        if (seconds < 120) return OrderStatus.ON_THE_WAY;
        return OrderStatus.DELIVERED;
    }

    public static String getStatusText(OrderStatus status) {
        return switch (status) {
            case PACKING -> "Your order is getting packed";
            case DELIVERY_PARTNER_PICKED -> "Delivery partner picked your order";
            case ON_THE_WAY -> "Your order is on the way";
            case DELIVERED -> "Order delivered successfully";
        };
    }
}

