package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.ProductDetailsDTO;
import com.infinitevision.infinite_store.service.ProductDetailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductDetailController {

    private final ProductDetailService productDetailService;

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<ProductDetailsDTO>> getProduct(@PathVariable Long id) {
        try {
            log.info("Fetching product details for ID: {}", id);
            ProductDetailsDTO productDetails = productDetailService.getProductDetails(id);

            if (productDetails == null) {
                log.warn("Product not found for ID: {}", id);
                return ResponseEntity.status(404)
                        .body(ApiResponse.failure("Product not found", 404));
            }

            log.info("Product details fetched successfully for ID: {}", id);
            return ResponseEntity.ok(ApiResponse.success("Product fetched successfully", productDetails));

        } catch (Exception e) {
            log.error("Error while fetching product details for ID: {}", id, e);
            return ResponseEntity.status(500)
                    .body(ApiResponse.error("Internal Server Error", 500));
        }
    }
}
