package com.example.carrental.model;

public class SUV extends Vehicle {
    private boolean fourWheelDrive;
    private int seatingCapacity;
    private double cargoSpace; // in cubic feet

    public SUV() {
        super();
    }

    public SUV(String vehicleId, String make, String model, int year, String registrationNumber,
               double dailyRate, boolean available, boolean fourWheelDrive, int seatingCapacity,
               double cargoSpace) {
        super(vehicleId, make, model, year, registrationNumber, dailyRate, available);
        this.fourWheelDrive = fourWheelDrive;
        this.seatingCapacity = seatingCapacity;
        this.cargoSpace = cargoSpace;
    }

    // Getters and Setters
    public boolean isFourWheelDrive() {
        return fourWheelDrive;
    }

    public void setFourWheelDrive(boolean fourWheelDrive) {
        this.fourWheelDrive = fourWheelDrive;
    }

    public int getSeatingCapacity() {
        return seatingCapacity;
    }

    public void setSeatingCapacity(int seatingCapacity) {
        this.seatingCapacity = seatingCapacity;
    }

    public double getCargoSpace() {
        return cargoSpace;
    }

    public void setCargoSpace(double cargoSpace) {
        this.cargoSpace = cargoSpace;
    }

    @Override
    public double calculateRentalCost(int days) {
        // Premium for 4WD SUVs
        if (fourWheelDrive) {
            return super.calculateRentalCost(days) * 1.15; // 15% premium
        }
        return super.calculateRentalCost(days);
    }

    @Override
    public String toString() {
        return super.toString() + "," + fourWheelDrive + "," + seatingCapacity + "," + cargoSpace;
    }
}