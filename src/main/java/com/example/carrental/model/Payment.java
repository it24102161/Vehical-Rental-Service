package com.example.carrental.model;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Payment implements Serializable {
    private String paymentId;
    private String bookingId;
    private double amount;
    private LocalDateTime paymentDate;
    private String paymentMethod;
    private String status; // pending, completed, refunded

    public Payment() {
    }

    public Payment(String paymentId, String bookingId, double amount,
                   LocalDateTime paymentDate, String paymentMethod, String status) {
        this.paymentId = paymentId;
        this.bookingId = bookingId;
        this.amount = amount;
        this.paymentDate = paymentDate;
        this.paymentMethod = paymentMethod;
        this.status = status;
    }

    // Getters and Setters
    public String getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(String paymentId) {
        this.paymentId = paymentId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return paymentId + "," + bookingId + "," + amount + "," +
                paymentDate + "," + paymentMethod + "," + status;
    }
}