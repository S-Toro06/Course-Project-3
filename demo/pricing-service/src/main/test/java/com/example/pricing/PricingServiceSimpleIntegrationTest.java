package com.example.pricing;


import com.example.pricing.controller.PricingController;
import com.example.pricing.dto.OrderPricingRequest;
import com.example.pricing.dto.PricingResponse;
import com.example.pricing.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class PricingServiceSimpleIntegrationTest {
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

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());

        double totalAmount = response.getBody().getTotalAmount();
        assertTrue(totalAmount > 0, "Total amount should be positive");
        assertTrue(totalAmount > 40, "Total for 4 people with luxury cabs should be substantial");

        double expectedBasePrice = 4 * 12.0;
        assertTrue(totalAmount > expectedBasePrice, "Total should exceed base price due to complexity and tax");

    }

    @Test
    void pricingCalculation_ShouldHandleVariousScenarios() {
        OrderPricingRequest basicRequest = new OrderPricingRequest();
        basicRequest.setCustomerName("Basic Customer");
        basicRequest.setCabs("Gas");
        basicRequest.setMiles(1);

        OrderPricingRequest luxuryRequest = new OrderPricingRequest();
        luxuryRequest.setCustomerName("Luxury Customer");
        luxuryRequest.setCabs("Luxury electric");
        luxuryRequest.setMiles(2);

        ResponseEntity<PricingResponse> basicResponse = pricingController.calculateOrderPrice(basicRequest);
        ResponseEntity<PricingResponse> luxuryResponse = pricingController.calculateOrderPrice(luxuryRequest);

        assertNotNull(basicResponse.getBody());
        assertNotNull(luxuryResponse.getBody());

        assertTrue(luxuryResponse.getBody().getTotalAmount() > basicResponse.getBody().getTotalAmount(),
                "Luxury cabs should cost more than basic items");
    }

    @Test
    void serviceHealthCheck_ShouldReturnPositiveStatus() {
        ResponseEntity<String> healthResponse = pricingController.healthCheck();

        assertEquals(200, healthResponse.getStatusCodeValue());
        assertEquals("Pricing Service is running!", healthResponse.getBody());
    }
}
