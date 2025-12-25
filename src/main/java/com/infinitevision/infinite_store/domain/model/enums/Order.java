package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

import lombok.*;
@Setter
@Getter
@Entity
@Table(name = "orders")
@Data
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id", unique = true)
    private String orderId;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "address_id")
    private Long addressId;

    @Column(name = "item_total")
    private Double itemTotal;

    @Column(name = "delivery_fee")
    private Double deliveryFee;

    @Column(name = "discount")
    private Double discount;

    @Column(name = "grand_total")
    private Double grandTotal;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private Payment payment;

    @Column(name = "on_time")
    private Boolean onTime;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
