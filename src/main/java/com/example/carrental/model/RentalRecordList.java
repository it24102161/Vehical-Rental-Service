// src/main/java/com/example/carrental/model/RentalRecordList.java
package com.example.carrental.model;

import com.example.carrental.model.RentalRecord;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class RentalRecordList implements Serializable {
    private RentalRecord head;
    private int size;

    public RentalRecordList() {
        this.head = null;
        this.size = 0;
    }

    // Add a new rental record to the beginning of the list
    public void addRecord(String vehicleId, String userId, LocalDate rentDate,
                          LocalDate returnDate, double rentalCost) {
        String recordId = UUID.randomUUID().toString();
        RentalRecord newRecord = new RentalRecord(recordId, vehicleId, userId,
                rentDate, returnDate, rentalCost);

        if (head == null) {
            head = newRecord;
        } else {
            newRecord.setNext(head);
            head = newRecord;
        }
        size++;
    }

    // Add a record at the end of the list
    public void appendRecord(RentalRecord record) {
        if (head == null) {
            head = record;
        } else {
            RentalRecord current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(record);
        }
        size++;
    }

    // Remove a record by its ID
    public boolean removeRecord(String recordId) {
        if (head == null) {
            return false;
        }

        if (head.getRecordId().equals(recordId)) {
            head = head.getNext();
            size--;
            return true;
        }

        RentalRecord current = head;
        while (current.getNext() != null && !current.getNext().getRecordId().equals(recordId)) {
            current = current.getNext();
        }

        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
            size--;
            return true;
        }

        return false;
    }

    // Find a record by its ID
    public RentalRecord findRecord(String recordId) {
        RentalRecord current = head;
        while (current != null) {
            if (current.getRecordId().equals(recordId)) {
                return current;
            }
            current = current.getNext();
        }
        return null;
    }

    // Get all records for a specific vehicle
    public List<RentalRecord> getRecordsByVehicleId(String vehicleId) {
        List<RentalRecord> vehicleRecords = new ArrayList<>();
        RentalRecord current = head;

        while (current != null) {
            if (current.getVehicleId().equals(vehicleId)) {
                vehicleRecords.add(current);
            }
            current = current.getNext();
        }

        return vehicleRecords;
    }

    // Get all records for a specific user
    public List<RentalRecord> getRecordsByUserId(String userId) {
        List<RentalRecord> userRecords = new ArrayList<>();
        RentalRecord current = head;

        while (current != null) {
            if (current.getUserId().equals(userId)) {
                userRecords.add(current);
            }
            current = current.getNext();
        }

        return userRecords;
    }

    // Get all records as a List
    public List<RentalRecord> getAllRecords() {
        List<RentalRecord> allRecords = new ArrayList<>();
        RentalRecord current = head;

        while (current != null) {
            allRecords.add(current);
            current = current.getNext();
        }

        return allRecords;
    }

    // Get the size of the list
    public int size() {
        return size;
    }

    // Check if the list is empty
    public boolean isEmpty() {
        return head == null;
    }

    // Clear the list
    public void clear() {
        head = null;
        size = 0;
    }

    // Convert the linked list to a string representation
    public List<String> toStringList() {
        List<String> recordStrings = new ArrayList<>();
        RentalRecord current = head;

        while (current != null) {
            recordStrings.add(current.toString());
            current = current.getNext();
        }

        return recordStrings;
    }
}