// src/main/java/com/example/carrental/repository/RentalRecordRepository.java
package com.example.carrental.repository;

import com.example.carrental.model.RentalRecord;
import com.example.carrental.model.RentalRecordList;
import com.example.carrental.util.FileUtils;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public class RentalRecordRepository {
    private static final String RECORDS_FILE = "src/main/resources/data/rental_records.txt";
    private RentalRecordList recordList;

    public RentalRecordRepository() {
        // Ensure the file exists
        FileUtils.createFileIfNotExists(RECORDS_FILE);
        recordList = new RentalRecordList();
        loadRecordsFromFile();
    }

    private void loadRecordsFromFile() {
        List<String> lines = FileUtils.readFromFile(RECORDS_FILE);
        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                RentalRecord record = new RentalRecord(
                        parts[0], // recordId
                        parts[1], // vehicleId
                        parts[2], // userId
                        LocalDate.parse(parts[3]), // rentDate
                        LocalDate.parse(parts[4]), // returnDate
                        Double.parseDouble(parts[5])  // rentalCost
                );
                recordList.appendRecord(record);
            }
        }
    }

    private void saveRecordsToFile() {
        // Clear the file
        FileUtils.clearFile(RECORDS_FILE);

        // Write all records
        List<String> recordStrings = recordList.toStringList();
        for (String recordString : recordStrings) {
            FileUtils.writeToFile(RECORDS_FILE, recordString, true);
        }
    }

    public RentalRecord addRecord(String vehicleId, String userId, LocalDate rentDate,
                                  LocalDate returnDate, double rentalCost) {
        recordList.addRecord(vehicleId, userId, rentDate, returnDate, rentalCost);
        saveRecordsToFile();
        return recordList.findRecord(vehicleId);
    }

    public boolean removeRecord(String recordId) {
        boolean removed = recordList.removeRecord(recordId);
        if (removed) {
            saveRecordsToFile();
        }
        return removed;
    }

    public Optional<RentalRecord> findRecordById(String recordId) {
        RentalRecord record = recordList.findRecord(recordId);
        return Optional.ofNullable(record);
    }

    public List<RentalRecord> findRecordsByVehicleId(String vehicleId) {
        return recordList.getRecordsByVehicleId(vehicleId);
    }

    public List<RentalRecord> findRecordsByUserId(String userId) {
        return recordList.getRecordsByUserId(userId);
    }

    public List<RentalRecord> findAllRecords() {
        return recordList.getAllRecords();
    }
}