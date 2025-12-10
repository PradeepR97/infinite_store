package com.infinitevision.infinite_store.controller;


import com.infinitevision.infinite_store.domain.model.enums.Product;
import com.infinitevision.infinite_store.repository.ProductRepository;
import com.infinitevision.infinite_store.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping("/all")
    public ResponseEntity<?> getProductCards() {
        return ResponseEntity.ok(productService.getProductCards());
    }


}

