package com.example.order.dto;

public class OrderResponse {

    private Long orderId;
    private String customerName;
    private String cabs;
    private Integer miles;
    private double totalPrice;
    private String status;

    public OrderResponse() {}

    public OrderResponse(Long orderId, String customerName, String cabs, Integer miles, double totalPrice, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.cabs = cabs;
        this.miles = miles;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
