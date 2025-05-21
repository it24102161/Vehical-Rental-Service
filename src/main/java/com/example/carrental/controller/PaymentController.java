//package com.example.carrental.controller;
//
//import com.example.carrental.model.AdminUser;
//import com.example.carrental.model.Booking;
//import com.example.carrental.model.Payment;
//import com.example.carrental.model.User;
//import com.example.carrental.service.BookingService;
//import com.example.carrental.service.PaymentService;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
//import jakarta.servlet.http.HttpSession;
//import java.util.List;
//import java.util.Optional;
//
//@Controller
//@RequestMapping("/payments")
//public class PaymentController {
//
//    @Autowired
//    private PaymentService paymentService;
//
//    @Autowired
//    private BookingService bookingService;
//
//    @GetMapping("/process/{bookingId}")
//    public String showPaymentForm(@PathVariable String bookingId, Model model, HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            return "redirect:/users/login";
//        }
//
//        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
//        if (bookingOpt.isPresent()) {
//            Booking booking = bookingOpt.get();
//            // Check if user is the owner of this booking
//            if (booking.getUserId().equals(loggedInUser.getUserId())) {
//                model.addAttribute("booking", booking);
//                model.addAttribute("paymentMethods", new String[]{"Credit Card", "Debit Card", "PayPal"});
//                return "payments/process";
//            }
//        }
//
//        return "redirect:/bookings/list";
//    }
//
//    @PostMapping("/process/{bookingId}")
//    public String processPayment(@PathVariable String bookingId, @RequestParam String paymentMethod,
//                                 HttpSession session, RedirectAttributes redirectAttributes) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            return "redirect:/users/login";
//        }
//
//        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
//        if (bookingOpt.isPresent()) {
//            Booking booking = bookingOpt.get();
//            // Check if user is the owner of this booking
//            if (booking.getUserId().equals(loggedInUser.getUserId())) {
//                try {
//                    Payment payment = paymentService.processPayment(bookingId, paymentMethod);
//                    redirectAttributes.addFlashAttribute("message", "Payment processed successfully");
//                    return "redirect:/payments/receipt/" + payment.getPaymentId();
//                } catch (Exception e) {
//                    redirectAttributes.addFlashAttribute("error", e.getMessage());
//                    return "redirect:/payments/process/" + bookingId;
//                }
//            }
//        }
//
//        return "redirect:/bookings/list";
//    }
//
//    @GetMapping("/receipt/{paymentId}")
//    public String showReceipt(@PathVariable String paymentId, Model model, HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            return "redirect:/users/login";
//        }
//
//        Optional<Payment> paymentOpt = paymentService.getPaymentById(paymentId);
//        if (paymentOpt.isPresent()) {
//            Payment payment = paymentOpt.get();
//
//            // Get booking to check ownership
//            Optional<Booking> bookingOpt = bookingService.getBookingById(payment.getBookingId());
//            if (bookingOpt.isPresent()) {
//                Booking booking = bookingOpt.get();
//
//                // Check if user is owner or admin
//                if (booking.getUserId().equals(loggedInUser.getUserId()) || loggedInUser instanceof AdminUser) {
//                    model.addAttribute("payment", payment);
//                    model.addAttribute("booking", booking);
//                    return "payments/receipt";
//                }
//            }
//        }
//
//        return "redirect:/bookings/list";
//    }
//
//    @GetMapping("/history")
//    public String paymentHistory(Model model, HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null) {
//            return "redirect:/users/login";
//        }
//
//        // Get user's bookings
//        List<Booking> bookings = bookingService.getBookingsByUserId(loggedInUser.getUserId());
//
//        // For each booking, get payments
//        model.addAttribute("bookings", bookings);
//        return "payments/history";
//    }
//
//    // Admin methods for payment management
//    @GetMapping("/admin/list")
//    public String adminListPayments(Model model, HttpSession session) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        List<Payment> payments = paymentService.getAllPayments();
//        model.addAttribute("payments", payments);
//        return "admin/payments/list";
//    }
//
//    @GetMapping("/admin/refund/{paymentId}")
//    public String refundPayment(@PathVariable String paymentId, HttpSession session,
//                                RedirectAttributes redirectAttributes) {
//        User loggedInUser = (User) session.getAttribute("loggedInUser");
//        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
//            return "redirect:/users/login";
//        }
//
//        try {
//            paymentService.refundPayment(paymentId);
//            redirectAttributes.addFlashAttribute("message", "Payment refunded successfully");
//        } catch (Exception e) {
//            redirectAttributes.addFlashAttribute("error", e.getMessage());
//        }
//
//        return "redirect:/payments/admin/list";
//    }
//}
//

package com.example.carrental.controller;

import com.example.carrental.model.AdminUser;
import com.example.carrental.model.Booking;
import com.example.carrental.model.Payment;
import com.example.carrental.model.User;
import com.example.carrental.service.BookingService;
import com.example.carrental.service.PaymentService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/payments")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private BookingService bookingService;

    @GetMapping("/process/{bookingId}")
    public String showPaymentForm(@PathVariable String bookingId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            // Check if user is the owner of this booking
            if (booking.getUserId().equals(loggedInUser.getUserId())) {
                model.addAttribute("booking", booking);
                model.addAttribute("paymentMethods", new String[]{"Credit Card", "Debit Card", "PayPal"});
                return "payments/process";
            }
        }

        return "redirect:/bookings/list";
    }

    @PostMapping("/process/{bookingId}")
    public String processPayment(@PathVariable String bookingId, @RequestParam String paymentMethod,
                                 HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<Booking> bookingOpt = bookingService.getBookingById(bookingId);
        if (bookingOpt.isPresent()) {
            Booking booking = bookingOpt.get();
            // Check if user is the owner of this booking
            if (booking.getUserId().equals(loggedInUser.getUserId())) {
                try {
                    Payment payment = paymentService.processPayment(bookingId, paymentMethod);
                    redirectAttributes.addFlashAttribute("message", "Payment processed successfully");
                    return "redirect:/payments/receipt/" + payment.getPaymentId();
                } catch (Exception e) {
                    redirectAttributes.addFlashAttribute("error", e.getMessage());
                    return "redirect:/payments/process/" + bookingId;
                }
            }
        }

        return "redirect:/bookings/list";
    }

    @GetMapping("/receipt/{paymentId}")
    public String showReceipt(@PathVariable String paymentId, Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<Payment> paymentOpt = paymentService.getPaymentById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();

            // Get booking to check ownership
            Optional<Booking> bookingOpt = bookingService.getBookingById(payment.getBookingId());
            if (bookingOpt.isPresent()) {
                Booking booking = bookingOpt.get();

                // Check if user is owner or admin
                if (booking.getUserId().equals(loggedInUser.getUserId()) || loggedInUser instanceof AdminUser) {
                    model.addAttribute("payment", payment);
                    model.addAttribute("booking", booking);
                    return "payments/receipt";
                }
            }
        }

        return "redirect:/bookings/list";
    }

    @GetMapping("/history")
    public String paymentHistory(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        // Get user's bookings
        List<Booking> bookings = bookingService.getBookingsByUserId(loggedInUser.getUserId());

        // Create a map to store payments for each booking
        Map<String, List<Payment>> paymentsByBookingId = new HashMap<>();

        // For each booking, get payments and add to the map
        for (Booking booking : bookings) {
            List<Payment> bookingPayments = paymentService.getPaymentsByBookingId(booking.getBookingId());
            paymentsByBookingId.put(booking.getBookingId(), bookingPayments);
        }

        model.addAttribute("bookings", bookings);
        model.addAttribute("paymentsByBookingId", paymentsByBookingId);

        return "payments/history";
    }

    // Admin methods for payment management
    @GetMapping("/admin/list")
    public String adminListPayments(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        List<Payment> payments = paymentService.getAllPayments();
        model.addAttribute("payments", payments);
        return "admin/payments/list";
    }

    @GetMapping("/admin/refund/{paymentId}")
    public String refundPayment(@PathVariable String paymentId, HttpSession session,
                                RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        try {
            paymentService.refundPayment(paymentId);
            redirectAttributes.addFlashAttribute("message", "Payment refunded successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/payments/admin/list";
    }
}