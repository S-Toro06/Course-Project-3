package com.example.order.dto;

public class OrderPricingRequest {
    private String customerName;
    private String cabs;
    private Integer miles;

    public OrderPricingRequest() {}

    public OrderPricingRequest(String customerName, String cabs, Integer miles) {
        this.customerName = customerName;
        this.cabs = cabs;
        this.miles = miles;
    }

   public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) {this.customerName = customerName;}

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
