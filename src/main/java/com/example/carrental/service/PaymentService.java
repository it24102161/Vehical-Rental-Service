package com.example.carrental.service;

import com.example.carrental.model.Booking;
import com.example.carrental.model.Payment;
import com.example.carrental.repository.BookingRepository;
import com.example.carrental.repository.PaymentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    public Payment processPayment(String bookingId, String paymentMethod) {
        // Check if booking exists
        Optional<Booking> bookingOpt = bookingRepository.findBookingById(bookingId);
        if (!bookingOpt.isPresent()) {
            throw new RuntimeException("Booking not found");
        }

        Booking booking = bookingOpt.get();
        if (!booking.getStatus().equals("pending")) {
            throw new RuntimeException("Cannot process payment for non-pending booking");
        }

        // Create payment
        Payment payment = new Payment();
        payment.setBookingId(bookingId);
        payment.setAmount(booking.getTotalCost());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setPaymentMethod(paymentMethod);
        payment.setStatus("completed");

        // Save payment
        payment = paymentRepository.savePayment(payment);

        // Update booking status
        booking.setStatus("active");
        bookingRepository.updateBooking(booking);

        return payment;
    }

    public List<Payment> getAllPayments() {
        return paymentRepository.findAllPayments();
    }

    public Optional<Payment> getPaymentById(String paymentId) {
        return paymentRepository.findPaymentById(paymentId);
    }

    public List<Payment> getPaymentsByBookingId(String bookingId) {
        return paymentRepository.findPaymentsByBookingId(bookingId);
    }

    public Payment refundPayment(String paymentId) {
        Optional<Payment> paymentOpt = paymentRepository.findPaymentById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();

            // Can only refund completed payments
            if (!payment.getStatus().equals("completed")) {
                throw new RuntimeException("Cannot refund payment with status: " + payment.getStatus());
            }

            payment.setStatus("refunded");
            paymentRepository.updatePayment(payment);

            // Cancel the associated booking
            bookingService.cancelBooking(payment.getBookingId());

            return payment;
        } else {
            throw new RuntimeException("Payment not found");
        }
    }
}