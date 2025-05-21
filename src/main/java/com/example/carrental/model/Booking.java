package com.example.carrental.model;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking implements Serializable {
    private String bookingId;
    private String userId;
    private String vehicleId;
    private LocalDate startDate;
    private LocalDate endDate;
    private double totalCost;
    private String status; // pending, active, completed, cancelled

    public Booking() {
    }

    public Booking(String bookingId, String userId, String vehicleId, LocalDate startDate,
                   LocalDate endDate, double totalCost, String status) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.vehicleId = vehicleId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.totalCost = totalCost;
        this.status = status;
    }

    // Getters and Setters
    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getRentalDays() {
        return (int) ChronoUnit.DAYS.between(startDate, endDate);
    }

    @Override
    public String toString() {
        return bookingId + "," + userId + "," + vehicleId + "," +
                startDate + "," + endDate + "," + totalCost + "," + status;
    }
}
