// src/main/java/com/example/carrental/controller/RentalRecordController.java
package com.example.carrental.controller;

import com.example.carrental.model.AdminUser;
import com.example.carrental.model.RentalRecord;
import com.example.carrental.model.User;
import com.example.carrental.model.Booking;
import com.example.carrental.service.RentalRecordService;
import com.example.carrental.service.BookingService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rental-records")
public class RentalRecordController {

    @Autowired
    private RentalRecordService rentalRecordService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/list")
    public String listRentalRecords(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        // Get all bookings and ensure rental records exist for them
        if (loggedInUser instanceof AdminUser) {
            List<Booking> bookings = bookingService.getAllBookings();
            for (Booking booking : bookings) {
                createRentalRecordIfNotExists(booking);
            }
        } else {
            List<Booking> bookings = bookingService.getBookingsByUserId(loggedInUser.getUserId());
            for (Booking booking : bookings) {
                createRentalRecordIfNotExists(booking);
            }
        }

        // Now get rental records
        List<RentalRecord> records;
        if (loggedInUser instanceof AdminUser) {
            records = rentalRecordService.getAllRentalRecords();
        } else {
            records = rentalRecordService.getRentalRecordsByUserId(loggedInUser.getUserId());
        }

        model.addAttribute("rentalRecords", records);
        return "rental-records/list";
    }

    private void createRentalRecordIfNotExists(Booking booking) {
        // Skip if booking isn't active or completed
        if (!booking.getStatus().equals("active") && !booking.getStatus().equals("completed")) {
            return;
        }

        // Check if rental record already exists for this booking
        List<RentalRecord> existingRecords = rentalRecordService.getRentalRecordsByVehicleId(booking.getVehicleId());
        boolean exists = false;

        for (RentalRecord record : existingRecords) {
            if (record.getUserId().equals(booking.getUserId()) &&
                    record.getRentDate().equals(booking.getStartDate()) &&
                    record.getReturnDate().equals(booking.getEndDate())) {
                exists = true;
                break;
            }
        }

        // Create rental record if it doesn't exist
        if (!exists) {
            rentalRecordService.createRentalRecord(
                    booking.getVehicleId(),
                    booking.getUserId(),
                    booking.getStartDate(),
                    booking.getEndDate(),
                    booking.getTotalCost()
            );
        }
    }

    @GetMapping("/vehicle/{vehicleId}")
    public String listVehicleRentalRecords(@PathVariable String vehicleId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        List<RentalRecord> records = rentalRecordService.getRentalRecordsByVehicleId(vehicleId);
        model.addAttribute("rentalRecords", records);
        model.addAttribute("vehicleId", vehicleId);
        return "rental-records/vehicle-history";
    }

    @GetMapping("/detail/{recordId}")
    public String rentalRecordDetail(@PathVariable String recordId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<RentalRecord> recordOpt = rentalRecordService.getRentalRecordById(recordId);
        if (recordOpt.isPresent()) {
            RentalRecord record = recordOpt.get();

            // Ensure the user can only see their own records unless they're an admin
            if (record.getUserId().equals(loggedInUser.getUserId()) || loggedInUser instanceof AdminUser) {
                model.addAttribute("rentalRecord", record);
                return "rental-records/detail";
            }
        }

        return "redirect:/rental-records/list";
    }

    // Admin methods
    @GetMapping("/admin/list")
    public String adminListRentalRecords(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        // Ensure rental records exist for all bookings
        List<Booking> bookings = bookingService.getAllBookings();
        for (Booking booking : bookings) {
            createRentalRecordIfNotExists(booking);
        }

        List<RentalRecord> records = rentalRecordService.getAllRentalRecords();
        model.addAttribute("rentalRecords", records);
        return "admin/rental-records/list";
    }

    @GetMapping("/admin/delete/{recordId}")
    public String deleteRentalRecord(@PathVariable String recordId, RedirectAttributes redirectAttributes, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        boolean deleted = rentalRecordService.deleteRentalRecord(recordId);
        if (deleted) {
            redirectAttributes.addFlashAttribute("message", "Rental record deleted successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to delete rental record");
        }

        return "redirect:/rental-records/admin/list";
    }
}