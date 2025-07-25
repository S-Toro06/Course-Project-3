package com.example.pricing.service;


import com.example.pricing.controller.PricingController;
import com.example.pricing.dto.OrderPricingRequest;
import com.example.pricing.dto.PricingResponse;
import com.example.pricing.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class PricingServiceTest {
    private PricingController pricingController;
    private PricingService pricingService;

    @BeforeEach
    void setUp() {
        pricingService = new PricingService();
        pricingController = new PricingController(pricingService);
    }

    @Test
    void endToPricingFlow_ShouldWorkCorrectly() {
        OrderPricingRequest request = new OrderPricingRequest();
        request.setCustomerName("Integration Customer");
        request.setCabs("Luxury Electric, Premium Gas");
        request.setMiles(4);

        ResponseEntity<PricingResponse> response = pricingController.calculateOrderPrice(request);
        
    }
}
