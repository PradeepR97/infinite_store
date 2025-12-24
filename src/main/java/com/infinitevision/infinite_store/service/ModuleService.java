package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.ModuleType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleService {
    private static final Logger logger = LoggerFactory.getLogger(ModuleService.class);


    public List<String> getAllModules() {
        logger.info("Fetching all modules from service layer");
        List<String> modules = Arrays.stream(ModuleType.values())
                .map(ModuleType::getDisplayName)
                .collect(Collectors.toList());
        logger.info("Modules fetched successfully: {}", modules);
        return modules;
    }
}

