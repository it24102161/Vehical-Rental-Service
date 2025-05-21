// src/main/java/com/example/carrental/service/RentalRecordService.java
package com.example.carrental.service;

import com.example.carrental.model.RentalRecord;
import com.example.carrental.repository.RentalRecordRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class RentalRecordService {

    @Autowired
    private RentalRecordRepository rentalRecordRepository;

    public RentalRecord createRentalRecord(String vehicleId, String userId, LocalDate rentDate,
                                           LocalDate returnDate, double rentalCost) {
        return rentalRecordRepository.addRecord(vehicleId, userId, rentDate, returnDate, rentalCost);
    }

    public boolean deleteRentalRecord(String recordId) {
        return rentalRecordRepository.removeRecord(recordId);
    }

    public Optional<RentalRecord> getRentalRecordById(String recordId) {
        return rentalRecordRepository.findRecordById(recordId);
    }

    public List<RentalRecord> getRentalRecordsByVehicleId(String vehicleId) {
        return rentalRecordRepository.findRecordsByVehicleId(vehicleId);
    }

    public List<RentalRecord> getRentalRecordsByUserId(String userId) {
        return rentalRecordRepository.findRecordsByUserId(userId);
    }

    public List<RentalRecord> getAllRentalRecords() {
        return rentalRecordRepository.findAllRecords();
    }
}