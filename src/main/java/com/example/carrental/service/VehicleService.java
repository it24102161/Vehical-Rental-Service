//package com.example.carrental.service;
//
//import com.example.carrental.model.Vehicle;
//import com.example.carrental.repository.VehicleRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class VehicleService {
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    public Vehicle addVehicle(Vehicle vehicle) {
//        return vehicleRepository.saveVehicle(vehicle);
//    }
//
//    public List<Vehicle> getAllVehicles() {
//        return vehicleRepository.findAllVehicles();
//    }
//
//    public Optional<Vehicle> getVehicleById(String vehicleId) {
//        return vehicleRepository.findVehicleById(vehicleId);
//    }
//
//    public List<Vehicle> getAvailableVehicles() {
//        return vehicleRepository.findAvailableVehicles();
//    }
//
//    public Vehicle updateVehicle(Vehicle vehicle) {
//        // Check if vehicle exists
//        Optional<Vehicle> existingVehicle = vehicleRepository.findVehicleById(vehicle.getVehicleId());
//        if (!existingVehicle.isPresent()) {
//            throw new RuntimeException("Vehicle not found");
//        }
//        return vehicleRepository.updateVehicle(vehicle);
//    }
//
//    public void deleteVehicle(String vehicleId) {
//        vehicleRepository.deleteVehicle(vehicleId);
//    }
//
//    public void setVehicleAvailability(String vehicleId, boolean isAvailable) {
//        Optional<Vehicle> vehicleOpt = vehicleRepository.findVehicleById(vehicleId);
//        if (vehicleOpt.isPresent()) {
//            Vehicle vehicle = vehicleOpt.get();
//            vehicle.setAvailable(isAvailable);
//            vehicleRepository.updateVehicle(vehicle);
//        } else {
//            throw new RuntimeException("Vehicle not found");
//        }
//    }
//}
// src/main/java/com/example/carrental/service/VehicleService.java
package com.example.carrental.service;

import com.example.carrental.model.Vehicle;
import com.example.carrental.repository.VehicleRepository;
import com.example.carrental.util.VehicleSorter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    @Autowired
    private VehicleRepository vehicleRepository;

    public Vehicle addVehicle(Vehicle vehicle) {
        return vehicleRepository.saveVehicle(vehicle);
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAllVehicles();
    }

    public List<Vehicle> getAllVehiclesSortedByPrice(boolean ascending) {
        List<Vehicle> vehicles = vehicleRepository.findAllVehicles();
        VehicleSorter.sortByPrice(vehicles, ascending);
        return vehicles;
    }

    public List<Vehicle> getAllVehiclesSortedByAvailability(boolean availableFirst) {
        List<Vehicle> vehicles = vehicleRepository.findAllVehicles();
        VehicleSorter.sortByAvailability(vehicles, availableFirst);
        return vehicles;
    }

    public List<Vehicle> getAllVehiclesSortedByAvailabilityAndPrice(boolean availableFirst, boolean ascendingPrice) {
        List<Vehicle> vehicles = vehicleRepository.findAllVehicles();
        VehicleSorter.sortByAvailabilityAndPrice(vehicles, availableFirst, ascendingPrice);
        return vehicles;
    }

    public Optional<Vehicle> getVehicleById(String vehicleId) {
        return vehicleRepository.findVehicleById(vehicleId);
    }

    public List<Vehicle> getAvailableVehicles() {
        return vehicleRepository.findAvailableVehicles();
    }

    public List<Vehicle> getAvailableVehiclesSortedByPrice(boolean ascending) {
        List<Vehicle> vehicles = vehicleRepository.findAvailableVehicles();
        VehicleSorter.sortByPrice(vehicles, ascending);
        return vehicles;
    }

    public Vehicle updateVehicle(Vehicle vehicle) {
        // Check if vehicle exists
        Optional<Vehicle> existingVehicle = vehicleRepository.findVehicleById(vehicle.getVehicleId());
        if (!existingVehicle.isPresent()) {
            throw new RuntimeException("Vehicle not found");
        }
        return vehicleRepository.updateVehicle(vehicle);
    }

    public void deleteVehicle(String vehicleId) {
        vehicleRepository.deleteVehicle(vehicleId);
    }

    public void setVehicleAvailability(String vehicleId, boolean isAvailable) {
        Optional<Vehicle> vehicleOpt = vehicleRepository.findVehicleById(vehicleId);
        if (vehicleOpt.isPresent()) {
            Vehicle vehicle = vehicleOpt.get();
            vehicle.setAvailable(isAvailable);
            vehicleRepository.updateVehicle(vehicle);
        } else {
            throw new RuntimeException("Vehicle not found");
        }
    }
}