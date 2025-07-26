package com.example.pricing.service;

import com.example.pricing.dto.OrderPricingRequest;
import com.example.pricing.dto.PricingResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PricingServiceTest {
    @InjectMocks
    private PricingService pricingService;
    private OrderPricingRequest pricingRequest;

    @BeforeEach
    void setUp() {
        pricingRequest = new OrderPricingRequest();
        pricingRequest.setCustomerName("Test Customer");
        pricingRequest.setMiles(2);
    }

    @Test
    void calculateOrderPrice_ShouldReturnBasicPrice_WhenSimpleCab() {
        pricingRequest.setCabs("Gas");

        PricingResponse result = pricingService.calculateOrderPrice(pricingRequest);

        assertNotNull(result);
        double expectedBasePrice = 2 * 2.0;
        double expectedComplexityFactor = 0.9;
        double expectedAdjustedPrice = expectedBasePrice * expectedComplexityFactor;
        double expectedTax = expectedAdjustedPrice * 0.8;
        double expectedTotal = expectedAdjustedPrice + expectedTax;

        assertEquals(expectedTotal, result.getTotalAmount(), 0.01);
        assertTrue(result.getTotalAmount() > 0);
    }

    @Test
    void calculateOrderPrice_ShouldReturnHigherPrice_WhenLuxuryCabs() {
        pricingRequest.setCabs("Luxury electric, Premium gas");
        pricingRequest.setMiles(4);

        PricingResponse result = pricingService.calculateOrderPrice(pricingRequest);

        assertNotNull(result);
        double expectedBasePrice = 4 * 4.0;
        double expectedComplexityFactor = 1.8;
        double expectedAdjustedPrice = expectedBasePrice * expectedComplexityFactor;
        double expectedTax = expectedAdjustedPrice * 0.8;
        double expectedTotal = expectedAdjustedPrice + expectedTax;

        assertEquals(expectedTotal, result.getTotalAmount(), 0.01);
        assertTrue(result.getTotalAmount() > 70);
    }

    @Test
    void calculateOrderPrice_ShouldHandleNullMenuItems_WhenNoCabs() {
        pricingRequest.setCabs(null);
        pricingRequest.setMiles(1);

        PricingResponse result = pricingService.calculateOrderPrice(pricingRequest);

        assertNotNull(result);
        double expectedBasePrice = 1 * 2.0;
        double expectedComplexityFactor = 1.0;
        double expectedAdjustedPrice = expectedBasePrice * expectedComplexityFactor;
        double expectedTax = expectedAdjustedPrice * 0.8;
        double expectedTotal = expectedAdjustedPrice + expectedTax;

        assertEquals(expectedTotal, result.getTotalAmount(), 0.01);
        assertEquals(12.96, result.getTotalAmount(), 0.01);
    }
}