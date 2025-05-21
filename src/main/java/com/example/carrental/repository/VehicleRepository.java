package com.example.carrental.repository;

import com.example.carrental.model.Car;
import com.example.carrental.model.SUV;
import com.example.carrental.model.Vehicle;
import com.example.carrental.util.FileUtils;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class VehicleRepository {
    private static final String VEHICLES_FILE = "src/main/resources/data/vehicles.txt";

    public VehicleRepository() {
        // Ensure the file exists
        FileUtils.createFileIfNotExists(VEHICLES_FILE);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        if (vehicle.getVehicleId() == null || vehicle.getVehicleId().isEmpty()) {
            vehicle.setVehicleId(UUID.randomUUID().toString());
        }

        FileUtils.writeToFile(VEHICLES_FILE, vehicle.toString(), true);
        return vehicle;
    }

    public List<Vehicle> findAllVehicles() {
        List<Vehicle> vehicles = new ArrayList<>();
        List<String> lines = FileUtils.readFromFile(VEHICLES_FILE);

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 7) {
                // Identify vehicle type from the content to create appropriate object
                if (parts.length >= 10 && parts[0].contains("CAR")) { // Car
                    Car car = new Car(
                            parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4],
                            Double.parseDouble(parts[5]), Boolean.parseBoolean(parts[6]),
                            parts[7], Integer.parseInt(parts[8]), parts[9]
                    );
                    vehicles.add(car);
                } else if (parts.length >= 10 && parts[0].contains("SUV")) { // SUV
                    SUV suv = new SUV(
                            parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4],
                            Double.parseDouble(parts[5]), Boolean.parseBoolean(parts[6]),
                            Boolean.parseBoolean(parts[7]), Integer.parseInt(parts[8]),
                            Double.parseDouble(parts[9])
                    );
                    vehicles.add(suv);
                } else { // Basic Vehicle
                    Vehicle vehicle = new Vehicle(
                            parts[0], parts[1], parts[2], Integer.parseInt(parts[3]), parts[4],
                            Double.parseDouble(parts[5]), Boolean.parseBoolean(parts[6])
                    );
                    vehicles.add(vehicle);
                }
            }
        }

        return vehicles;
    }

    public Optional<Vehicle> findVehicleById(String vehicleId) {
        return findAllVehicles().stream()
                .filter(vehicle -> vehicle.getVehicleId().equals(vehicleId))
                .findFirst();
    }

    public List<Vehicle> findAvailableVehicles() {
        return findAllVehicles().stream()
                .filter(Vehicle::isAvailable)
                .collect(Collectors.toList());
    }

    public void deleteVehicle(String vehicleId) {
        Optional<Vehicle> vehicleOpt = findVehicleById(vehicleId);
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            FileUtils.removeFromFile(VEHICLES_FILE, vehicleId);
        }
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        Optional<Vehicle> existingVehicleOpt = findVehicleById(vehicle.getVehicleId());
        if (existingVehicleOpt.isPresent()) {
            Vehicle existingVehicle = existingVehicleOpt.get();
            FileUtils.updateInFile(VEHICLES_FILE, existingVehicle.getVehicleId(), vehicle.toString());
        }
        return vehicle;
    }
}