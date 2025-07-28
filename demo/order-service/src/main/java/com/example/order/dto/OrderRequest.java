package com.example.order.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Cab service is required")
    private String cabs;

    @NotNull(message = "Number of miles is required")
    @Positive (message = "Number of miles must be positive")
    private Integer miles;

    public OrderRequest() {}

    public OrderRequest(String customerName, String cabs, Integer miles) {
        this.customerName = customerName;
        this.cabs = cabs;
        this.miles = miles;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCabs() {
        return cabs;
    }

    public void setCabs(String cabs) {
        this.cabs = cabs;
    }

    public Integer getMiles() {
        return miles;
    }

    public void setMiles(Integer miles) {
        this.miles = miles;
    }
}
