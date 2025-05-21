package com.example.carrental.controller;

import com.example.carrental.model.AdminUser;
import com.example.carrental.model.Booking;
import com.example.carrental.model.User;
import com.example.carrental.model.Vehicle;
import com.example.carrental.service.BookingService;
import com.example.carrental.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/create/{vehicleId}")
    public String showBookingForm(@PathVariable String vehicleId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
        if (vehicleOpt.isPresent() && vehicleOpt.get().isAvailable()) {
            model.addAttribute("vehicle", vehicleOpt.get());
            model.addAttribute("minDate", LocalDate.now());
            model.addAttribute("maxDate", LocalDate.now().plusMonths(6));
            return "bookings/create";
        } else {
            return "redirect:/vehicles/available";
        }
    }

    @PostMapping("/create/{vehicleId}")
    public String createBooking(@PathVariable String vehicleId,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        try {
            Booking booking = bookingService.createBooking(loggedInUser.getUserId(), vehicleId, startDate, endDate);
            redirectAttributes.addFlashAttribute("message", "Booking created successfully! Please proceed to payment.");
            return "redirect:/bookings/details/" + booking.getBookingId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bookings/create/" + vehicleId;
        }
    }

    @GetMapping("/list")
    public String listUserBookings(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        List<Booking> bookings = bookingService.getBookingsByUserId(loggedInUser.getUserId());
        model.addAttribute("bookings", bookings);
        return "bookings/list";
    }

    @GetMapping("/details/{bookingId}")
    public String bookingDetails(@PathVariable String bookingId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            // Check if user is the owner of this booking or an admin
            if (booking.getUserId().equals(loggedInUser.getUserId()) || loggedInUser instanceof AdminUser) {
                model.addAttribute("booking", booking);

                // Get vehicle details
                Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(booking.getVehicleId());
                vehicleOpt.ifPresent(vehicle -> model.addAttribute("vehicle", vehicle));

                return "bookings/details";
            }
        }

        return "redirect:/bookings/list";
    }

    @GetMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable String bookingId, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        try {
            Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
            if (bookingOpt.isPresent()) {
                Booking booking = bookingOpt.get();
                // Check if user is the owner of this booking or an admin
                if (booking.getUserId().equals(loggedInUser.getUserId()) || loggedInUser instanceof AdminUser) {
                    bookingService.cancelBooking(bookingId);
                    redirectAttributes.addFlashAttribute("message", "Booking cancelled successfully");
                } else {
                    redirectAttributes.addFlashAttribute("error", "You are not authorized to cancel this booking");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Booking not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/bookings/list";
    }

    // Admin methods for booking management
    @GetMapping("/admin/list")
    public String adminListBookings(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        List<Booking> bookings = bookingService.getAllBookings();
        model.addAttribute("bookings", bookings);
        return "admin/bookings/list";
    }

    @GetMapping("/admin/complete/{bookingId}")
    public String completeBooking(@PathVariable String bookingId, HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        try {
            bookingService.completeBooking(bookingId);
            redirectAttributes.addFlashAttribute("message", "Booking marked as completed successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/bookings/admin/list";
    }
}

package com.example.carrental.controller;

import com.example.carrental.model.AdminUser;
import com.example.carrental.model.Booking;
import com.example.carrental.model.User;
import com.example.carrental.model.Vehicle;
import com.example.carrental.service.BookingService;
import com.example.carrental.service.VehicleService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/create/{vehicleId}")
    public String showBookingForm(@PathVariable String vehicleId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(vehicleId);
        if (vehicleOpt.isPresent() && vehicleOpt.get().isAvailable()) {
            model.addAttribute("vehicle", vehicleOpt.get());
            model.addAttribute("minDate", LocalDate.now());
            model.addAttribute("maxDate", LocalDate.now().plusMonths(6));
            return "bookings/create";
        } else {
            return "redirect:/vehicles/available";
        }
    }

    @PostMapping("/create/{vehicleId}")
    public String createBooking(@PathVariable String vehicleId,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                                @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
                                HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        try {
            Booking booking = bookingService.createBooking(loggedInUser.getUserId(), vehicleId, startDate, endDate);
            redirectAttributes.addFlashAttribute("message", "Booking created successfully! Please proceed to payment.");
            return "redirect:/bookings/details/" + booking.getBookingId();
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/bookings/create/" + vehicleId;
        }
    }

    @GetMapping("/list")
    public String listUserBookings(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String dateRange,
            Model model,
            HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        List<Booking> bookings;

        // Apply filters if specified
        if ((status != null && !status.isEmpty()) || (dateRange != null && !dateRange.isEmpty())) {
            bookings = bookingService.getFilteredBookingsByUserId(
                    loggedInUser.getUserId(),
                    status,
                    dateRange
            );
        } else {
            bookings = bookingService.getBookingsByUserId(loggedInUser.getUserId());
        }

        model.addAttribute("bookings", bookings);

        // Preserve filter values for form
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedDateRange", dateRange);

        return "bookings/list";
    }

    @GetMapping("/details/{bookingId}")
    public String bookingDetails(@PathVariable String bookingId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            // Check if user is the owner of this booking or an admin
            if (booking.getUserId().equals(loggedInUser.getUserId()) || loggedInUser instanceof AdminUser) {
                model.addAttribute("booking", booking);

                // Get vehicle details
                Optional<Vehicle> vehicleOpt = vehicleService.getVehicleById(booking.getVehicleId());
                vehicleOpt.ifPresent(vehicle -> model.addAttribute("vehicle", vehicle));

                return "bookings/details";
            }
        }

        return "redirect:/bookings/list";
    }

    @GetMapping("/cancel/{bookingId}")
    public String cancelBooking(@PathVariable String bookingId, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        try {
            Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
            if (bookingOpt.isPresent()) {
                Booking booking = bookingOpt.get();
                // Check if user is the owner of this booking or an admin
                if (booking.getUserId().equals(loggedInUser.getUserId()) || loggedInUser instanceof AdminUser) {
                    bookingService.cancelBooking(bookingId);
                    redirectAttributes.addFlashAttribute("message", "Booking cancelled successfully");
                } else {
                    redirectAttributes.addFlashAttribute("error", "You are not authorized to cancel this booking");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Booking not found");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/bookings/list";
    }

    // Admin methods for booking management
    @GetMapping("/admin/list")
    public String adminListBookings(
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String dateRange,
            Model model,
            HttpSession session) {

        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        List<Booking> bookings;

        // Apply filters if specified
        if ((status != null && !status.isEmpty()) || (dateRange != null && !dateRange.isEmpty())) {
            bookings = bookingService.getFilteredBookings(status, dateRange);
        } else {
            bookings = bookingService.getAllBookings();
        }

        model.addAttribute("bookings", bookings);

        // Preserve filter values for form
        model.addAttribute("selectedStatus", status);
        model.addAttribute("selectedDateRange", dateRange);

        return "admin/bookings/list";
    }

    @GetMapping("/admin/complete/{bookingId}")
    public String completeBooking(@PathVariable String bookingId, HttpSession session,
                                  RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        try {
            bookingService.completeBooking(bookingId);
            redirectAttributes.addFlashAttribute("message", "Booking marked as completed successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/bookings/admin/list";
    }
}