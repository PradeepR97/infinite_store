package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.dto.ApiResponse;
import com.infinitevision.infinite_store.dto.ProductDetailsDTO;
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

    // Fetch all products with full details
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<?>> getAllProducts() {

        log.info("Request received: Fetching ALL product details");

        List<ProductDetailsDTO> products = productService.getAllProducts();

        log.debug("Fetched {} products", products.size());

        return ResponseEntity.ok(
                ApiResponse.success("All product details fetched successfully", products)
        );
    }
}
