package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.ProductCardDTO;
import com.infinitevision.infinite_store.service.ProductService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/products")

public class ProductController {

    private static final Logger log = LoggerFactory.getLogger(ProductController.class);

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getProductCards() {

        log.info("Request received: Fetching PRODUCT CARD details");

        List<ProductCardDTO> cards = productService.getAllProductCards();

        log.debug("Fetched {} product cards", cards.size());

        return ResponseEntity.ok(
                ApiResponse.success("Product card data fetched successfully", cards)
        );
    }

}
