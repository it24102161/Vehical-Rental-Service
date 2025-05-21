//package com.example.carrental.service;
//
//import com.example.carrental.model.Booking;
//import com.example.carrental.model.Vehicle;
//import com.example.carrental.repository.BookingRepository;
//import com.example.carrental.repository.VehicleRepository;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.time.LocalDate;
//import java.util.List;
//import java.util.Optional;
//
//@Service
//public class BookingService {
//
//    @Autowired
//    private BookingRepository bookingRepository;
//
//    @Autowired
//    private VehicleRepository vehicleRepository;
//
//    @Autowired
//    private VehicleService vehicleService;
//
//    public Booking createBooking(String userId, String vehicleId, LocalDate startDate, LocalDate endDate) {
//        // Check if vehicle exists and is available
//        Optional<Vehicle> vehicleOpt = vehicleRepository.findVehicleById(vehicleId);
//        if (!vehicleOpt.isPresent()) {
//            throw new RuntimeException("Vehicle not found");
//        }
//
//        Vehicle vehicle = vehicleOpt.get();
//        if (!vehicle.isAvailable()) {
//            throw new RuntimeException("Vehicle is not available for booking");
//        }
//
//        // Calculate rental cost
//        int days = (int) java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
//        if (days <= 0) {
//            throw new RuntimeException("Invalid date range");
//        }
//
//        double totalCost = vehicle.calculateRentalCost(days);
//
//        // Create booking
//        Booking booking = new Booking();
//        booking.setUserId(userId);
//        booking.setVehicleId(vehicleId);
//        booking.setStartDate(startDate);
//        booking.setEndDate(endDate);
//        booking.setTotalCost(totalCost);
//        booking.setStatus("pending");
//
//        // Save booking
//        booking = bookingRepository.saveBooking(booking);
//
//        // Update vehicle availability
//        vehicleService.setVehicleAvailability(vehicleId, false);
//
//        return booking;
//    }
//
//    public List<Booking> getAllBookings() {
//        return bookingRepository.findAllBookings();
//    }
//
//    public Optional<Booking> getBookingById(String bookingId) {
//        return bookingRepository.findBookingById(bookingId);
//    }
//
//    public List<Booking> getBookingsByUserId(String userId) {
//        return bookingRepository.findBookingsByUserId(userId);
//    }
//
//    public List<Booking> getActiveBookings() {
//        return bookingRepository.findActiveBookings();
//    }
//
//    public void cancelBooking(String bookingId) {
//        Optional<Booking> bookingOpt = bookingRepository.findBookingById(bookingId);
//        if (bookingOpt.isPresent()) {
//            Booking booking = bookingOpt.get();
//            booking.setStatus("cancelled");
//            bookingRepository.updateBooking(booking);
//
//            // Make vehicle available again
//            vehicleService.setVehicleAvailability(booking.getVehicleId(), true);
//        } else {
//            throw new RuntimeException("Booking not found");
//        }
//    }
//
//    public Booking completeBooking(String bookingId) {
//        Optional<Booking> bookingOpt = bookingRepository.findBookingById(bookingId);
//        if (bookingOpt.isPresent()) {
//            Booking booking = bookingOpt.get();
//            booking.setStatus("completed");
//            bookingRepository.updateBooking(booking);
//
//            // Make vehicle available again
//            vehicleService.setVehicleAvailability(booking.getVehicleId(), true);
//
//            return booking;
//        } else {
//            throw new RuntimeException("Booking not found");
//        }
//    }
//}
package com.example.carrental.service;

import com.example.carrental.model.Booking;
import com.example.carrental.model.Vehicle;
import com.example.carrental.repository.BookingRepository;
import com.example.carrental.repository.VehicleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private VehicleRepository vehicleRepository;

    @Autowired
    private VehicleService vehicleService;

    public Booking createBooking(String userId, String vehicleId, LocalDate startDate, LocalDate endDate) {
        // Check if vehicle exists and is available
        Optional<Vehicle> vehicleOpt = vehicleRepository.findVehicleById(vehicleId);
        if (!vehicleOpt.isPresent()) {
            throw new RuntimeException("Vehicle not found");
        }

        Vehicle vehicle = vehicleOpt.get();
        if (!vehicle.isAvailable()) {
            throw new RuntimeException("Vehicle is not available for booking");
        }

        // Calculate rental cost
        int days = (int) java.time.temporal.ChronoUnit.DAYS.between(startDate, endDate);
        if (days <= 0) {
            throw new RuntimeException("Invalid date range");
        }

        double totalCost = vehicle.calculateRentalCost(days);

        // Create booking
        Booking booking = new Booking();
        booking.setUserId(userId);
        booking.setVehicleId(vehicleId);
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        booking.setTotalCost(totalCost);
        booking.setStatus("pending");

        // Save booking
        booking = bookingRepository.saveBooking(booking);

        // Update vehicle availability
        vehicleService.setVehicleAvailability(vehicleId, false);

        return booking;
    }

    public List<Booking> getAllBookings() {
        return bookingRepository.findAllBookings();
    }

    public Optional<Booking> getBookingById(String bookingId) {
        return bookingRepository.findBookingById(bookingId);
    }

    public List<Booking> getBookingsByUserId(String userId) {
        return bookingRepository.findBookingsByUserId(userId);
    }

    public List<Booking> getFilteredBookingsByUserId(String userId, String status, String dateRange) {
        List<Booking> bookings = bookingRepository.findBookingsByUserId(userId);

        // Apply status filter if provided
        if (status != null && !status.isEmpty()) {
            bookings = bookings.stream()
                    .filter(booking -> booking.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        // Apply date range filter if provided
        if (dateRange != null && !dateRange.isEmpty()) {
            LocalDate today = LocalDate.now();

            switch (dateRange) {
                case "current":
                    // Current & upcoming bookings (end date >= today)
                    bookings = bookings.stream()
                            .filter(booking -> booking.getEndDate().isEqual(today) || booking.getEndDate().isAfter(today))
                            .collect(Collectors.toList());
                    break;
                case "past":
                    // Past bookings (end date < today)
                    bookings = bookings.stream()
                            .filter(booking -> booking.getEndDate().isBefore(today))
                            .collect(Collectors.toList());
                    break;
                case "last30":
                    // Bookings in the last 30 days
                    LocalDate thirtyDaysAgo = today.minusDays(30);
                    bookings = bookings.stream()
                            .filter(booking ->
                                    (booking.getStartDate().isAfter(thirtyDaysAgo) || booking.getStartDate().isEqual(thirtyDaysAgo))
                                            && (booking.getStartDate().isBefore(today) || booking.getStartDate().isEqual(today)))
                            .collect(Collectors.toList());
                    break;
            }
        }

        return bookings;
    }

    public List<Booking> getFilteredBookings(String status, String dateRange) {
        List<Booking> bookings = bookingRepository.findAllBookings();

        // Apply status filter if provided
        if (status != null && !status.isEmpty()) {
            bookings = bookings.stream()
                    .filter(booking -> booking.getStatus().equalsIgnoreCase(status))
                    .collect(Collectors.toList());
        }

        // Apply date range filter if provided
        if (dateRange != null && !dateRange.isEmpty()) {
            LocalDate today = LocalDate.now();

            switch (dateRange) {
                case "current":
                    // Current & upcoming bookings (end date >= today)
                    bookings = bookings.stream()
                            .filter(booking -> booking.getEndDate().isEqual(today) || booking.getEndDate().isAfter(today))
                            .collect(Collectors.toList());
                    break;
                case "past":
                    // Past bookings (end date < today)
                    bookings = bookings.stream()
                            .filter(booking -> booking.getEndDate().isBefore(today))
                            .collect(Collectors.toList());
                    break;
                case "last30":
                    // Bookings in the last 30 days
                    LocalDate thirtyDaysAgo = today.minusDays(30);
                    bookings = bookings.stream()
                            .filter(booking ->
                                    (booking.getStartDate().isAfter(thirtyDaysAgo) || booking.getStartDate().isEqual(thirtyDaysAgo))
                                            && (booking.getStartDate().isBefore(today) || booking.getStartDate().isEqual(today)))
                            .collect(Collectors.toList());
                    break;
            }
        }

        return bookings;
    }

    public List<Booking> getActiveBookings() {
        return bookingRepository.findActiveBookings();
    }

    public void cancelBooking(String bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus("cancelled");
            bookingRepository.updateBooking(booking);

            // Make vehicle available again
            vehicleService.setVehicleAvailability(booking.getVehicleId(), true);
        } else {
            throw new RuntimeException("Booking not found");
        }
    }

    public Booking completeBooking(String bookingId) {
        Optional<Booking> bookingOpt = bookingRepository.findBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            booking.setStatus("completed");
            bookingRepository.updateBooking(booking);

            // Make vehicle available again
            vehicleService.setVehicleAvailability(booking.getVehicleId(), true);

            return booking;
        } else {
            throw new RuntimeException("Booking not found");
        }
    }
}