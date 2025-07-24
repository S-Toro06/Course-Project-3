package com.example.order.dto;

public class OrderResponse {

    private Long orderId;
    private String customerName;
    private String Cabs;
    private Integer Miles;
    private double totalPrice;
    private String status;

    public OrderResponse() {}

    public OrderResponse(Long orderId, String customerName, String Cabs, Integer Miles, double totalPrice, String status) {
        this.orderId = orderId;
        this.customerName = customerName;
        this.Cabs = Cabs;
        this.Miles = Miles;
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
