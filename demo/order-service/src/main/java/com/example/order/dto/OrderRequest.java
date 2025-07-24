package com.example.order.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class OrderRequest {
    @NotBlank(message = "Customer name is required")
    private String customerName;

    @NotBlank(message = "Cab service is required")
    private String Cabs;

    @NotNull(message = "Number of miles is required")
    @Positive (message = "Number of miles must be positive")
    private Integer Miles;

    public OrderRequest() {}

    public OrderRequest(String customerName, String Cabs, Integer Miles) {
        this.customerName = customerName;
        this.Cabs = Cabs;
        this.Miles = Miles;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCabs() {
        return Cabs;
    }

    public void setCabs(String cabs) {
        Cabs = cabs;
    }

    public Integer getMiles() {
        return Miles;
    }

    public void setMiles(Integer miles) {
        Miles = miles;
    }
}
