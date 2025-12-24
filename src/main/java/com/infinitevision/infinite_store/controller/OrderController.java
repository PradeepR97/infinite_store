package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.PlaceOrderRequest;
import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    private final OrderService orderService;

    @PostMapping("/place")
    public ResponseEntity<ApiResponse<?>> placeOrder(@RequestBody PlaceOrderRequest request) {
        log.info("Received order placement request for userId={}", request.getUserId());

        try {
            var orderResponse = orderService.placeOrder(request);
            log.info("Order placed successfully for userId={}, orderId={}", request.getUserId(), orderResponse.getData().getOrderId());

            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(ApiResponse.success("Order placed successfully", orderResponse.getData()));
        } catch (Exception e) {
            log.error("Failed to place order for userId={}: {}", request.getUserId(), e.getMessage(), e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(ApiResponse.error("Failed to place order: " + e.getMessage(), 500));
        }
    }
}
