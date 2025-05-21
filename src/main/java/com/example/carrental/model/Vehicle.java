package com.example.carrental.model;

import java.io.Serializable;

public class Vehicle implements Serializable {
    private String vehicleId;
    private String make;
    private String model;
    private int year;
    private String registrationNumber;
    private double dailyRate;
    private boolean available;

    public Vehicle() {
    }

    public Vehicle(String vehicleId, String make, String model, int year,
                   String registrationNumber, double dailyRate, boolean available) {
        this.vehicleId = vehicleId;
        this.make = make;
        this.model = model;
        this.year = year;
        this.registrationNumber = registrationNumber;
        this.dailyRate = dailyRate;
        this.available = available;
    }

    // Getters and Setters
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public double getDailyRate() {
        return dailyRate;
    }

    public void setDailyRate(double dailyRate) {
        this.dailyRate = dailyRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public double calculateRentalCost(int days) {
        return dailyRate * days;
    }

    @Override
    public String toString() {
        return vehicleId + "," + make + "," + model + "," + year + "," +
                registrationNumber + "," + dailyRate + "," + available;
    }
}