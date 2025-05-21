package com.example.carrental.model;

public class Car extends Vehicle {
    private String bodyType; // sedan, hatchback, etc.
    private int numDoors;
    private String transmission; // automatic, manual

    public Car() {
        super();
    }

    public Car(String vehicleId, String make, String model, int year, String registrationNumber,
               double dailyRate, boolean available, String bodyType, int numDoors, String transmission) {
        super(vehicleId, make, model, year, registrationNumber, dailyRate, available);
        this.bodyType = bodyType;
        this.numDoors = numDoors;
        this.transmission = transmission;
    }

    // Getters and Setters
    public String getBodyType() {
        return bodyType;
    }

    public void setBodyType(String bodyType) {
        this.bodyType = bodyType;
    }

    public int getNumDoors() {
        return numDoors;
    }

    public void setNumDoors(int numDoors) {
        this.numDoors = numDoors;
    }

    public String getTransmission() {
        return transmission;
    }

    public void setTransmission(String transmission) {
        this.transmission = transmission;
    }

    @Override
    public double calculateRentalCost(int days) {
        // Apply discount for longer rentals
        if (days > 7) {
            return super.calculateRentalCost(days) * 0.9; // 10% discount
        }
        return super.calculateRentalCost(days);
    }

    @Override
    public String toString() {
        return super.toString() + "," + bodyType + "," + numDoors + "," + transmission;
    }
}
