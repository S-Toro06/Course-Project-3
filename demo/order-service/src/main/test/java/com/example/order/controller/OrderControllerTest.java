package com.example.order.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.order.dto.OrderRequest;
import com.example.order.dto.OrderResponse;
import com.example.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
public class OrderControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private OrderRequest orderRequest;
    private OrderResponse orderResponse;

    @BeforeEach
    void setUp() {
        orderRequest = new OrderRequest();
        orderRequest.setCustomerName("Craig Thompson");
        orderRequest.setCabs("Electric, Luxury");
        orderRequest.setMiles(3);

        orderResponse = new OrderResponse(1L, "Craig Thompson", "Electric, Luxury", 3, 55.75, "CONFIRMED");
    }

    @Test
    void createOrder_ShouldReturnOrderResponse_WhenValidRequestProvided() throws Exception {
        when(orderService.createOrder(any(OrderRequest.class))).thenReturn(orderResponse);

        mockMvc.perform(post("/api/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(orderRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.orderId").value(1))
                .andExpect(jsonPath("$.customerName").value("Craig Thompson"))
                .andExpect(jsonPath("$.Cabs").value("Electric, Luxury"))
                .andExpect(jsonPath("$.Miles").value(3))
                .andExpect(jsonPath("$.totalPrice").value(55.75))
                .andExpect(jsonPath("$.status").value("CONFIRMED"));
    }

    @Test
    void createOrder_ShouldReturnRequest_WhenInvalidRequestProvided() throws Exception {
        OrderRequest invalidRequest = new OrderRequest();

        mockMvc.perform(post("/api/orders'")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidRequest)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.error").value("Validation failed"));
    }
}
