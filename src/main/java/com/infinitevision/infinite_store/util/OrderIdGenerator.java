package com.infinitevision.infinite_store.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class OrderIdGenerator {

    private OrderIdGenerator() {} // prevent object creation

    public static String generate(Long sequence) {
        String datePart = LocalDate.now()
                .format(DateTimeFormatter.ofPattern("ddMMyy"));
        return datePart + String.format("%04d", sequence);
    }
}
