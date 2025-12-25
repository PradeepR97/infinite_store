package com.infinitevision.infinite_store.domain.model.enums;

public enum Payment {
    UPI("UPI"),
    PLUXEE("PLUXEE"),
    CREDITCARD("CREDIT DEBIT CARD"),
    PAYLATER("PAYLATER"),
    WALLETS("WALLETS"),
    NETBANKING("NETBANKING"),
    PAYONDELIVERY("PAY ON DELIVERY");

    private final String displayName;

    Payment(String displayName) {
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


