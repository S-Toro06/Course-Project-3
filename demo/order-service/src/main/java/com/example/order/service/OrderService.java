package com.example.order.service;

import com.example.order.dto.OrderRequest;
import com.example.order.dto.OrderResponse;
import com.example.order.dto.OrderPricingRequest;
import com.example.order.dto.PricingResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClientException;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class OrderService {
    private final RestTemplate restTemplate;
    private final Map<Long, OrderResponse> orders = new ConcurrentHashMap<>();
    private final AtomicLong orderIdCounter = new AtomicLong();

    @Value("${pricing.service-url}")
    private String pricingServiceUrl;

    @Autowired
    public OrderService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public OrderResponse createOrder(OrderRequest request) {
        Long orderId = orderIdCounter.getAndIncrement();

        OrderPricingRequest pricingRequest = new OrderPricingRequest();
        pricingRequest.setCustomerName(request.getCustomerName());
        pricingRequest.setCabs(request.getCabs());
        pricingRequest.setMiles(request.getMiles());

        PricingResponse pricingResponse = null;
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<OrderPricingRequest> entity = new HttpEntity<>(pricingRequest, headers);
            ResponseEntity<PricingResponse> response = restTemplate.postForEntity(pricingServiceUrl, entity, PricingResponse.class);
            pricingResponse = response.getBody();
        } catch (RestClientException e) {
            System.err.println("Pricing service unavailable: " + e.getMessage());
        }

        String status = pricingResponse != null ? "CONFIRMED" : "PENDING_PRICING";
        double totalAmount = pricingResponse != null ? pricingResponse.getTotalAmount() : 0.0;

        OrderResponse orderResponse = new OrderResponse(
                orderId,
                request.getCustomerName(),
                request.getCabs(),
                request.getMiles(),
                totalAmount,
                status
        );

        orders.put(orderId, orderResponse);
        return orderResponse;
    }
}
