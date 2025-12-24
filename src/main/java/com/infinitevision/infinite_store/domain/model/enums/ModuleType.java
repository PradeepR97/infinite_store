package com.infinitevision.infinite_store.domain.model.enums;

public enum ModuleType {
    ORDER("Order"),
    CUSTOMER_SUPPORT("Customer Support"),
    MANAGE("Manage"),
    REFERRAL("Referral"),
    ADDRESS("Address"),
    PROFILE("Profile");

    private final String displayName;

    ModuleType(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}

