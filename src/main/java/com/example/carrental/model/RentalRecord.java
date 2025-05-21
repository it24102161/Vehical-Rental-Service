// src/main/java/com/example/carrental/model/RentalRecord.java
package com.example.carrental.model;

import java.io.Serializable;
import java.time.LocalDate;

public class RentalRecord implements Serializable {
    private String recordId;
    private String vehicleId;
    private String userId;
    private LocalDate rentDate;
    private LocalDate returnDate;
    private double rentalCost;
    private RentalRecord next; // Reference to the next node in the linked list

    public RentalRecord() {
        this.next = null;
    }

    public RentalRecord(String recordId, String vehicleId, String userId,
                        LocalDate rentDate, LocalDate returnDate, double rentalCost) {
        this.recordId = recordId;
        this.vehicleId = vehicleId;
        this.userId = userId;
        this.rentDate = rentDate;
        this.returnDate = returnDate;
        this.rentalCost = rentalCost;
        this.next = null;
    }

    // Getters and Setters
    public String getRecordId() {
        return recordId;
    }

    public void setRecordId(String recordId) {
        this.recordId = recordId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public LocalDate getRentDate() {
        return rentDate;
    }

    public void setRentDate(LocalDate rentDate) {
        this.rentDate = rentDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public double getRentalCost() {
        return rentalCost;
    }

    public void setRentalCost(double rentalCost) {
        this.rentalCost = rentalCost;
    }

    public RentalRecord getNext() {
        return next;
    }

    public void setNext(RentalRecord next) {
        this.next = next;
    }

    @Override
    public String toString() {
        return recordId + "," + vehicleId + "," + userId + "," +
                rentDate + "," + returnDate + "," + rentalCost;
    }
}

