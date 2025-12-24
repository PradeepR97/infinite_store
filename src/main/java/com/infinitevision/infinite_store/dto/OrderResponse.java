package com.infinitevision.infinite_store.dto;

import com.infinitevision.infinite_store.domain.model.enums.OrderStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class OrderResponse {

    private String orderId;
    private OrderStatus orderStatus;
    private int arrivalTimeInSeconds;
    private String deliveryStatusText;
    private boolean onTime;

    private BillSummary billSummary;
    private Payment payment;
    private Address deliveryAddress;
    private Tracking tracking;
    private List<Item> items;

    @Getter @Setter
    public static class BillSummary {
        private Double itemTotal;
        private Double deliveryFee;
        private Double discount;
        private Double grandTotal;
    }

    @Getter @Setter
    public static class Payment {
        private String paymentMethod;
        private String paymentStatus;
        private String message;
    }

    @Getter @Setter
    public static class Address {
        private String addressLine1;
        private String addressLine2;
        private String landmark;
        private String city;
        private String state;
        private String pincode;
        private String addressType;
    }

    @Getter
    @Setter
    public static class Tracking {
        private boolean enabled;
    }

    @Getter @Setter
    public static class Item {
        private Long productId;
        private String productName;
        private Integer quantity;
        private Double price;
        private Double totalPrice;
    }
}

