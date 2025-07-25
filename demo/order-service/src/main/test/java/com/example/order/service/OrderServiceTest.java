package com.example.order.service;


import com.example.order.dto.OrderRequest;
import com.example.order.dto.OrderResponse;
import com.example.order.dto.OrderPricingRequest;
import com.example.order.dto.PricingResponse;
import org.apache.coyote.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class OrderServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private OrderService orderService;
    private OrderRequest orderRequest;
    private PricingResponse pricingResponse;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(orderService, "pricingServiceUrl", "http://localhost:8081/api/pricing/calculate");

        orderRequest.setCustomerName("John Smith");
        orderRequest.setCabs("Premium, Gas");
        orderRequest.setMiles(2);

        pricingResponse = new PricingResponse();
        pricingResponse.setTotalAmount(45.50);
    }

    @Test
    void createOrder_ShouldReturnOrderWithCalculatedPrice_WhenPricingServiceReturnsPrice() {
        ResponseEntity<PricingResponse> responseEntity = new ResponseEntity<>(pricingResponse, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class)))
                .thenReturn(responseEntity);

        OrderResponse result = orderService.createOrder(orderRequest);


        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals("John Smith", result.getCustomerName());
        assertEquals("Premium, Gas", result.getCabs());
        assertEquals(2, result.getMiles());
        assertEquals(45.50, result.getTotalPrice());
        assertEquals("CONFIRMED", result.getStatus());

        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class));
    }

    @Test
    void createOrder_ShouldReturnOrderWithZeroPrice_WhenPricingServiceThrowsException() {
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class)))
                .thenThrow(new RestClientException("Service unavailable"));

        OrderResponse result = orderService.createOrder(orderRequest);

        assertNotNull(result);
        assertEquals(1L, result.getOrderId());
        assertEquals("John Smith", result.getCustomerName());
        assertEquals("Premium, Gas", result.getCabs());
        assertEquals(2, result.getMiles());
        assertEquals(0.0, result.getTotalPrice());
        assertEquals("PENDING_PRICING", result.getStatus());

        verify(restTemplate, times(1)).postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class));
    }

    @Test
    void createOrder_ShouldGenerateUniqueOrderIds_WhenMultipleOrdersCreated() {
        ResponseEntity<PricingResponse> responseEntity = new ResponseEntity<>(pricingResponse, HttpStatus.OK);
        when(restTemplate.postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class)))
                .thenReturn(responseEntity);

        OrderResponse firstOrder = orderService.createOrder(orderRequest);
        OrderResponse secondOrder = orderService.createOrder(orderRequest);

        assertNotNull(firstOrder);
        assertNotNull(secondOrder);
        assertNotEquals(firstOrder.getOrderId(), secondOrder.getOrderId());
        assertEquals(1L, firstOrder.getOrderId());
        assertEquals(2L, secondOrder.getOrderId());

        verify(restTemplate, times(2)).postForEntity(anyString(), any(HttpEntity.class), eq(PricingResponse.class));
    }
}
