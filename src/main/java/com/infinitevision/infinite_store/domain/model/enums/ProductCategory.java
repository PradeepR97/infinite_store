package com.infinitevision.infinite_store.domain.model.enums;

public enum ProductCategory {
    ALL("All"),
    CAFE("Cafe"),
    HOME("Home"),
    TOYS("Toys"),
    FRESH("Fresh"),
    ELECTRONICS("Electronics"),
    MOBILES("Mobiles"),
    BEAUTY("Beauty"),
    FASHION("Fashion");

    private final String displayName;

    ProductCategory(String displayName){
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}

