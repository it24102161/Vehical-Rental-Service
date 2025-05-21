package com.example.carrental.repository;

import com.example.carrental.model.Booking;
import com.example.carrental.util.FileUtils;

import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class BookingRepository {
    private static final String BOOKINGS_FILE = "src/main/resources/data/bookings.txt";

    public BookingRepository() {
        // Ensure the file exists
        FileUtils.createFileIfNotExists(BOOKINGS_FILE);
    }

    public Booking saveBooking(Booking booking) {
        if (booking.getBookingId() == null || booking.getBookingId().isEmpty()) {
            booking.setBookingId(UUID.randomUUID().toString());
        }

        FileUtils.writeToFile(BOOKINGS_FILE, booking.toString(), true);
        return booking;
    }

    public List<Booking> findAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        List<String> lines = FileUtils.readFromFile(BOOKINGS_FILE);

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 7) {
                Booking booking = new Booking(
                        parts[0], parts[1], parts[2],
                        LocalDate.parse(parts[3]), LocalDate.parse(parts[4]),
                        Double.parseDouble(parts[5]), parts[6]
                );
                bookings.add(booking);
            }
        }

        return bookings;
    }

    public Optional<Booking> findBookingById(String bookingId) {
        return findAllBookings().stream()
                .filter(booking -> booking.getBookingId().equals(bookingId))
                .findFirst();
    }

    public List<Booking> findBookingsByUserId(String userId) {
        return findAllBookings().stream()
                .filter(booking -> booking.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    public List<Booking> findActiveBookings() {
        return findAllBookings().stream()
                .filter(booking -> booking.getStatus().equals("active"))
                .collect(Collectors.toList());
    }

    public void deleteBooking(String bookingId) {
        Optional<Booking> bookingOpt = findBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            FileUtils.removeFromFile(BOOKINGS_FILE, bookingId);
        }
    }

    public Booking updateBooking(Booking booking) {
        Optional<Booking> existingBookingOpt = findBookingById(booking.getBookingId());
        if (existingBookingOpt.isPresent()) {
            Booking existingBooking = existingBookingOpt.get();
            FileUtils.updateInFile(BOOKINGS_FILE, existingBooking.getBookingId(), booking.toString());
        }
        return booking;
    }
}