package com.example.carrental.repository;

import com.example.carrental.model.Payment;
import com.example.carrental.util.FileUtils;

import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class PaymentRepository {
    private static final String PAYMENTS_FILE = "src/main/resources/data/payments.txt";
    private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

    public PaymentRepository() {
        // Ensure the file exists
        FileUtils.createFileIfNotExists(PAYMENTS_FILE);
    }

    public Payment savePayment(Payment payment) {
        if (payment.getPaymentId() == null || payment.getPaymentId().isEmpty()) {
            payment.setPaymentId(UUID.randomUUID().toString());
        }

        FileUtils.writeToFile(PAYMENTS_FILE, payment.toString(), true);
        return payment;
    }

    public List<Payment> findAllPayments() {
        List<Payment> payments = new ArrayList<>();
        List<String> lines = FileUtils.readFromFile(PAYMENTS_FILE);

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 6) {
                Payment payment = new Payment(
                        parts[0], parts[1], Double.parseDouble(parts[2]),
                        LocalDateTime.parse(parts[3], DATE_FORMAT), parts[4], parts[5]
                );
                payments.add(payment);
            }
        }

        return payments;
    }

    public Optional<Payment> findPaymentById(String paymentId) {
        return findAllPayments().stream()
                .filter(payment -> payment.getPaymentId().equals(paymentId))
                .findFirst();
    }

    public List<Payment> findPaymentsByBookingId(String bookingId) {
        return findAllPayments().stream()
                .filter(payment -> payment.getBookingId().equals(bookingId))
                .collect(Collectors.toList());
    }

    public void deletePayment(String paymentId) {
        Optional<Payment> paymentOpt = findPaymentById(paymentId);
        if (paymentOpt.isPresent()) {
            Payment payment = paymentOpt.get();
            FileUtils.removeFromFile(PAYMENTS_FILE, paymentId);
        }
    }

    public Payment updatePayment(Payment payment) {
        Optional<Payment> existingPaymentOpt = findPaymentById(payment.getPaymentId());
        if (existingPaymentOpt.isPresent()) {
            Payment existingPayment = existingPaymentOpt.get();
            FileUtils.updateInFile(PAYMENTS_FILE, existingPayment.getPaymentId(), payment.toString());
        }
        return payment;
    }
}