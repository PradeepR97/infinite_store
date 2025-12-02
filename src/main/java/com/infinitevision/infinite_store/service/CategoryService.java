package com.infinitevision.infinite_store.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    private static final Logger logger = LoggerFactory.getLogger(CategoryService.class);

    public List<String> getCategories() {
        logger.info("Service: Fetching categories from enum...");
        return List.of("All", "Cafe", "Home");
    }
}
