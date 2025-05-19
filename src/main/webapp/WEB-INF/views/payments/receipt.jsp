<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment Receipt - Car Rental Service</title>
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

<!-- Payment Receipt -->
<div class="container mt-4">
    <div class="row">
        <div class="col-md-8 offset-md-2">
            <div class="card">
                <div class="card-header bg-success text-white">
                    <h4 class="mb-0">Payment Successful!</h4>
                </div>
                <div class="card-body">
                    <div class="text-center mb-4">
                        <div class="display-1 text-success mb-3">
                            <i class="bi bi-check-circle"></i>
                        </div>
                        <h5>Thank you for your payment!</h5>
                        <p>Your booking has been confirmed and is now active.</p>
                    </div>

                    <!-- Receipt Details -->
                    <div class="row">
                        <div class="col-md-6">
                            <h5>Payment Information</h5>
                            <table class="table table-bordered">
                                <tbody>
                                <tr>
                                    <th style="width: 40%">Receipt #</th>
                                    <td>${payment.paymentId}</td>
                                </tr>
                                <tr>
                                    <th>Date & Time</th>
                                    <td>
                                        <fmt:parseDate value="${payment.paymentDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
                                        <fmt:formatDate value="${parsedDate}" pattern="MMM dd, yyyy HH:mm" />
                                    </td>
                                </tr>
                                <tr>
                                    <th>Amount Paid</th>
                                    <td>$${payment.amount}</td>
                                </tr>
                                <tr>
                                    <th>Payment Method</th>
                                    <td>${payment.paymentMethod}</td>
                                </tr>
                                <tr>
                                    <th>Status</th>
                                    <td>
                                                <span class="badge bg-success">
                                                    ${payment.status}
                                                </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>

                        <div class="col-md-6">
                            <h5>Booking Details</h5>
                            <table class="table table-bordered">
                                <tbody>
                                <tr>
                                    <th style="width: 40%">Booking #</th>
                                    <td>${booking.bookingId}</td>
                                </tr>
                                <tr>
                                    <th>Vehicle</th>
                                    <td>${booking.vehicleId}</td>
                                </tr>
                                <tr>
                                    <th>Start Date</th>
                                    <td>${booking.startDate}</td>
                                </tr>
                                <tr>
                                    <th>End Date</th>
                                    <td>${booking.endDate}</td>
                                </tr>
                                <tr>
                                    <th>Status</th>
                                    <td>
                                                <span class="badge bg-success">
                                                    ${booking.status}
                                                </span>
                                    </td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>

                    <!-- Actions -->
                    <div class="d-grid gap-2 mt-4">
                        <a href="<c:url value='/bookings/details/${booking.bookingId}'/>" class="btn btn-primary">View Booking Details</a>
                        <a href="<c:url value='/dashboard'/>" class="btn btn-outline-primary">Go to Dashboard</a>
                    </div>
                </div>
                <div class="card-footer text-center">
                    <small>A copy of this receipt has been sent to your email address.</small>
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
</body>
</html>