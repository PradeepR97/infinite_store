package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.domain.model.enums.ProductCategory;
import com.infinitevision.infinite_store.dto.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MetadataController {

    private static final Logger logger = LoggerFactory.getLogger(MetadataController.class);

    @GetMapping("/metadata")
    public ResponseEntity<ApiResponse<List<Map<String, String>>>> getCategories() {

        logger.info("Fetching product categories...");

        try {
            List<Map<String, String>> categories = new ArrayList<>();

            for (ProductCategory category : ProductCategory.values()) {
                Map<String, String> map = new HashMap<>();
                map.put(category.getDisplayName(), category.getDisplayName());
                categories.add(map);
            }

            logger.info("Categories fetched successfully: {}", categories);

            return ResponseEntity.ok(
                    ApiResponse.success("Categories fetched successfully", categories)
            );

        } catch (Exception e) {
            logger.error("Failed to fetch categories", e);

            return ResponseEntity.internalServerError().body(
                    ApiResponse.failure("Failed to fetch categories", 500)
            );
        }
    }
}
