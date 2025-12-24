package com.infinitevision.infinite_store.controller;

import com.infinitevision.infinite_store.domain.model.enums.ModuleType;
import com.infinitevision.infinite_store.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestController
@RequestMapping("/api/profile")
public class ModuleController {

    private static final Logger logger = LoggerFactory.getLogger(ModuleController.class);

    @GetMapping
    public ResponseEntity<ApiResponse<List<String>>> getAllModules() {
        logger.info("Fetching all modules");
        List<String> modules = Arrays.stream(ModuleType.values())
                .map(ModuleType::getDisplayName)
                .collect(Collectors.toList());
        logger.info("Modules fetched successfully: {}", modules);
        return ResponseEntity.ok(ApiResponse.success("Modules fetched successfully", modules));
    }
}
