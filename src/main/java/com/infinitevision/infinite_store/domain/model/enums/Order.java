package com.infinitevision.infinite_store.domain.model.enums;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


import lombok.*;



@Entity
@Table(name = "orders")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 10 digit order id like 091025XXXX
    @Column(unique = true)
    private String orderId;

    private Long userId;

    private Long addressId;

    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;

    @Enumerated(EnumType.STRING)
    private Payment payment;

    private Boolean onTime;

    /* ---------- BILL SUMMARY ---------- */
    private Double itemTotal;
    private Double deliveryFee;
    private Double discount;
    private Double grandTotal;
}
