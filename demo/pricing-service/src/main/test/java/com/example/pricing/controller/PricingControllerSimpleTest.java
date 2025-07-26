package com.example.pricing.controller;

import com.example.pricing.dto.OrderPricingRequest;
import com.example.pricing.dto.PricingResponse;
import com.example.pricing.service.PricingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;

public class PricingControllerSimpleTest {
    private PricingController pricingController;
    private PricingService pricingService;
    private OrderPricingRequest pricingRequest;

    @BeforeEach
    void setUp() {
        pricingService = new PricingService();
        pricingController = new PricingController(pricingService);

        pricingRequest = new OrderPricingRequest();
        pricingRequest.setCustomerName("Test Customer");
        pricingRequest.setCabs("Gas");
        pricingRequest.setMiles(3);
    }

    @Test
    void calculateOrderPrice_ShouldReturnOkResponse_WhenValidRequestProvided() {
        ResponseEntity<PricingResponse> response = pricingController.calculateOrderPrice(pricingRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTotalAmount() > 0);
    }

    @Test
    void healthCheck_ShouldReturnOnWithMessage() {
        ResponseEntity<String> response = pricingController.healthCheck();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Pricing Service is running", response.getBody());
    }

    @Test
    void calculateOrderPrice_ShouldHandleLuxuryCabs() {
        pricingRequest.setCabs("Luxury electric, Premium Gas");
        pricingRequest.setMiles(2);

        ResponseEntity<PricingResponse> response = pricingController.calculateOrderPrice(pricingRequest);

        assertEquals(200, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().getTotalAmount() > 30);
    }
}
