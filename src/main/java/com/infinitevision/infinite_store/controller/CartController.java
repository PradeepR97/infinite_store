package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.ProductCardDTO;
import com.infinitevision.infinite_store.service.CartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;


    @PostMapping("/add/{productId}")
    public ResponseEntity<ApiResponse<String>> addToCart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long productId) {

        log.info("Add to cart request received for productId={}", productId);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Add to cart failed: Missing or invalid Authorization header");
            return ResponseEntity.status(401)
                    .body(ApiResponse.failure("Token is required", 401));
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            cartService.addToCart(token, productId);
            log.info("Product {} added to cart successfully", productId);
            return ResponseEntity.ok(
                    ApiResponse.success("Product added to cart successfully", null)
            );
        } catch (Exception ex) {
            log.error("Error while adding product {} to cart", productId, ex);
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Failed to add product to cart", 500));
        }
    }


    @GetMapping
    public ResponseEntity<ApiResponse<List<ProductCardDTO>>> getCart(
            @RequestHeader(value = "Authorization", required = false) String authHeader) {

        log.info("Get cart request received");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("Get cart failed: Missing or invalid Authorization header");
            return ResponseEntity.status(401)
                    .body(ApiResponse.failure("Token is required", 401));
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            List<ProductCardDTO> cartItems = cartService.getCart(token);
            log.info("Cart fetched successfully, itemsCount={}", cartItems.size());

            return ResponseEntity.ok(
                    ApiResponse.success("Cart items fetched successfully", cartItems)
            );
        } catch (Exception ex) {
            log.error("Error while fetching cart", ex);
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Failed to fetch cart", 500));
        }
    }
    @DeleteMapping("/remove/{productId}")
    public ResponseEntity<ApiResponse<String>> removeFromCart(
            @RequestHeader(value = "Authorization", required = false) String authHeader,
            @PathVariable Long productId) {

        log.info("Remove from cart request for productId={}", productId);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return ResponseEntity.status(401)
                    .body(ApiResponse.failure("Token is required", 401));
        }

        String token = authHeader.replace("Bearer ", "");

        try {
            cartService.removeFromCart(token, productId);
            return ResponseEntity.ok(
                    ApiResponse.success("Product removed from cart", null)
            );
        } catch (Exception ex) {
            log.error("Error removing product from cart", ex);
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Failed to remove product from cart", 500));
        }
    }

}