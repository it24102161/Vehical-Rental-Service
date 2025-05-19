<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Process - Car Rental Service</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="<c:url value='/static/styles.css'/>">
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-primary">
    <div class="container">
        <a class="navbar-brand" href="<c:url value='/'/>">Car Rental Service</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/'/>">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/vehicles/available'/>">Available Vehicles</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/dashboard'/>">Dashboard</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/users/profile'/>">Profile</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/users/logout'/>">Logout</a>
                </li>
            </ul>
        </div>
    </div>
</nav>

<!-- Alert for messages -->
<c:if test="${not empty message}">
    <div class="alert alert-success alert-dismissible fade show" role="alert">
            ${message}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>
<c:if test="${not empty error}">
    <div class="alert alert-danger alert-dismissible fade show" role="alert">
            ${error}
        <button type="button" class="btn-close" data-bs-dismiss="alert" aria-label="Close"></button>
    </div>
</c:if>

<!-- Payment Process -->
<div class="container mt-4">
    <div class="row">
        <div class="col-md-8 offset-md-2">
            <div class="card">
                <div class="card-header">
                    <h4 class="mb-0">Payment Information</h4>
                </div>
                <div class="card-body">
                    <!-- Booking Summary -->
                    <div class="mb-4">
                        <h5>Booking Summary</h5>
                        <table class="table table-bordered">
                            <tbody>
                            <tr>
                                <th style="width: 30%">Booking ID</th>
                                <td>${booking.bookingId}</td>
                            </tr>
                            <tr>
                                <th>Vehicle</th>
                                <td>${booking.vehicleId}</td>
                            </tr>
                            <tr>
                                <th>Rental Period</th>
                                <td>${booking.startDate} to ${booking.endDate} (${booking.rentalDays} days)</td>
                            </tr>
                            <tr>
                                <th>Total Amount</th>
                                <td class="fw-bold">$${booking.totalCost}</td>
                            </tr>
                            </tbody>
                        </table>
                    </div>

                    <!-- Payment Form -->
                    <form action="<c:url value='/payments/process/${booking.bookingId}'/>" method="post" class="needs-validation" novalidate>
                        <h5>Payment Method</h5>

                        <div class="mb-3">
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="creditCard" value="Credit Card" checked>
                                <label class="form-check-label" for="creditCard">Credit Card</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="debitCard" value="Debit Card">
                                <label class="form-check-label" for="debitCard">Debit Card</label>
                            </div>
                            <div class="form-check form-check-inline">
                                <input class="form-check-input" type="radio" name="paymentMethod" id="paypal" value="PayPal">
                                <label class="form-check-label" for="paypal">PayPal</label>
                            </div>
                        </div>

                        <!-- Credit Card Information (show/hide based on payment method) -->
                        <div id="cardPaymentForm">
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="cardName" class="form-label">Name on Card</label>
                                    <input type="text" class="form-control" id="cardName" required>
                                    <div class="invalid-feedback">
                                        Please enter the name on card.
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="cardNumber" class="form-label">Card Number</label>
                                    <input type="text" class="form-control" id="cardNumber" placeholder="XXXX XXXX XXXX XXXX" required>
                                    <div class="invalid-feedback">
                                        Please enter a valid card number.
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-4">
                                    <label for="expiryMonth" class="form-label">Expiry Month</label>
                                    <select class="form-select" id="expiryMonth" required>
                                        <option value="" selected disabled>Month</option>
                                        <option value="01">01</option>
                                        <option value="02">02</option>
                                        <option value="03">03</option>
                                        <option value="04">04</option>
                                        <option value="05">05</option>
                                        <option value="06">06</option>
                                        <option value="07">07</option>
                                        <option value="08">08</option>
                                        <option value="09">09</option>
                                        <option value="10">10</option>
                                        <option value="11">11</option>
                                        <option value="12">12</option>
                                    </select>
                                    <div class="invalid-feedback">
                                        Please select expiry month.
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <label for="expiryYear" class="form-label">Expiry Year</label>
                                    <select class="form-select" id="expiryYear" required>
                                        <option value="" selected disabled>Year</option>
                                        <option value="2025">2025</option>
                                        <option value="2026">2026</option>
                                        <option value="2027">2027</option>
                                        <option value="2028">2028</option>
                                        <option value="2029">2029</option>
                                        <option value="2030">2030</option>
                                    </select>
                                    <div class="invalid-feedback">
                                        Please select expiry year.
                                    </div>
                                </div>
                                <div class="col-md-4">
                                    <label for="cvv" class="form-label">CVV</label>
                                    <input type="text" class="form-control" id="cvv" placeholder="123" required>
                                    <div class="invalid-feedback">
                                        Please enter CVV.
                                    </div>
                                </div>
                            </div>
                        </div>

                        <!-- PayPal Information (show/hide based on payment method) -->
                        <div id="paypalForm" style="display: none;">
                            <div class="alert alert-info">
                                <p class="mb-0">You will be redirected to PayPal to complete your payment securely.</p>
                            </div>
                        </div>

                        <div class="mb-3 form-check">
                            <input type="checkbox" class="form-check-input" id="termsAgree" required>
                            <label class="form-check-label" for="termsAgree">I agree to the terms and conditions</label>
                            <div class="invalid-feedback">
                                You must agree before submitting.
                            </div>
                        </div>

                        <div class="d-grid gap-2 mt-4">
                            <button type="submit" class="btn btn-success btn-lg">Complete Payment</button>
                            <a href="<c:url value='/bookings/details/${booking.bookingId}'/>" class="btn btn-outline-secondary">Cancel</a>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white mt-5 py-3">
    <div class="container text-center">
        <p class="mb-0">&copy; 2025 Car Rental Service. All rights reserved.</p>
    </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value='/static/scripts.js'/>"></script>

<!-- Payment Method Toggle Script -->
<script>
    document.addEventListener('DOMContentLoaded', function() {
        const creditCardRadio = document.getElementById('creditCard');
        const debitCardRadio = document.getElementById('debitCard');
        const paypalRadio = document.getElementById('paypal');
        const cardPaymentForm = document.getElementById('cardPaymentForm');
        const paypalForm = document.getElementById('paypalForm');

        function togglePaymentForm() {
            if (creditCardRadio.checked || debitCardRadio.checked) {
                cardPaymentForm.style.display = 'block';
                paypalForm.style.display = 'none';
            } else if (paypalRadio.checked) {
                cardPaymentForm.style.display = 'none';
                paypalForm.style.display = 'block';
            }
        }

        creditCardRadio.addEventListener('change', togglePaymentForm);
        debitCardRadio.addEventListener('change', togglePaymentForm);
        paypalRadio.addEventListener('change', togglePaymentForm);
    });
</script>
</body>
</html>