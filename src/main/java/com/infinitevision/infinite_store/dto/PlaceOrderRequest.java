package com.infinitevision.infinite_store.dto;

import com.infinitevision.infinite_store.domain.model.enums.Payment;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PlaceOrderRequest {

    private Long userId;
    private Long addressId;
    private Payment payment;

    private AddAddressRequestDTO address;
    private List<Item> items;

    @Getter @Setter
    public static class Item {
        private Long productId;   // required
        private Integer quantity;
    }
}

