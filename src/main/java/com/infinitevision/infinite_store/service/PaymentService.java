package com.infinitevision.infinite_store.service;

import com.infinitevision.infinite_store.domain.model.enums.Payment;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PaymentService {

    public List<String> getPaymentKeys() {
        return Arrays.stream(Payment.values())
                .map(Payment::getDisplayName)
                .toList();
    }

}

