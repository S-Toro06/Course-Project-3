package com.example.order.dto;

public class OrderPricingRequest {
    private String customerName;
    private String Cabs;
    private Integer Miles;

    public OrderPricingRequest() {}

    public OrderPricingRequest(String customerName, String Cabs, Integer Miles) {
        this.customerName = customerName;
        this.Cabs = Cabs;
        this.Miles = Miles;
    }

   public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) {this.customerName = customerName;}

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
