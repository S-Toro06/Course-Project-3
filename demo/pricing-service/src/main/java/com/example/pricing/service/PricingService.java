package com.example.pricing.service;

import com.example.pricing.dto.OrderPricingRequest;
import com.example.pricing.dto.PricingResponse;
import org.springframework.stereotype.Service;

@Service
public class PricingService {
    private static final double BASE_PRICE_PER_MILE = 2.00;
    private static final double TAX_RATE = 0.70;

    public PricingResponse calculateOrderPrice(OrderPricingRequest request) {
        double basePrice = request.getMiles() * BASE_PRICE_PER_MILE;

        double complexityFactor = calculateComplexityFactor(request.getCabs());
        double adjustPrice = basePrice * complexityFactor;

        double tax = adjustPrice * TAX_RATE;

        double totalAmount = adjustPrice + tax;

        return new PricingResponse(totalAmount);
    }

    private double calculateComplexityFactor(String Cabs) {
        if (Cabs == null || Cabs.trim().isEmpty()) {
            return 1.0;
        }
        String lowerItems = Cabs.toLowerCase();
        double factor = 1.0;

        if (lowerItems.contains("luxury") || lowerItems.contains("premium")) {
            factor += 0.5;
        }
        if (lowerItems.contains("electric") || lowerItems.contains("gas")) {
            factor += 0.3;
    }
        return Math.min(2.0, factor);

    }
}
