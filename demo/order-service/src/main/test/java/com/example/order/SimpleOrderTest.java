package com.example.order;

import com.example.order.dto.OrderRequest;
import com.example.order.dto.OrderResponse;
import com.example.order.dto.PricingResponse;
import com.example.order.service.OrderService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class SimpleOrderTest {

    @Autowired
    private OrderService orderService;

    @MockBean
    private RestTemplate restTemplate;

    @Test
    void createOrder_ShouldWork() {
        PricingResponse pricingResponse = new PricingResponse();
        pricingResponse.setTotalAmount(25.99);
        ResponseEntity<PricingResponse> responseEntity = ResponseEntity.ok(pricingResponse);

        when(restTemplate.postForEntity(anyString(), any(), eq(PricingResponse.class)))
                .thenReturn(responseEntity);

        OrderRequest request = new OrderRequest();
        request.setCustomerName("Test Customer");
        request.setCabs("Electric");
        request.setMiles(5);

        OrderResponse response = orderService.createOrder(request);

        assertNotNull(response);
        assertEquals("Test Customer", response.getCustomerName());
        assertEquals("Electric", response.getCabs());
        assertEquals(5, response.getMiles());
        assertEquals(25.99, response.getTotalPrice());
        assertEquals("CONFIRMED", response.getStatus());
    }
}
