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
        public ResponseEntity<ApiResponse<?>> placeOrder(
                @RequestHeader("Authorization") String authorizationHeader,
                @RequestBody PlaceOrderRequest request
        ) {
            log.info("Received order placement request");

            try {
                var orderResponse = orderService.placeOrder(authorizationHeader, request);

                log.info("Order placed successfully, orderId={}", orderResponse.getData().getOrderId());

                return ResponseEntity.status(HttpStatus.CREATED)
                        .body(ApiResponse.success("Order placed successfully", orderResponse.getData()));
            } catch (Exception e) {
                log.error("Failed to place order: {}", e.getMessage(), e);
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(ApiResponse.error(e.getMessage(), 401));
            }
        }
    }
