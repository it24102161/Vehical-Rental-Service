<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Payment History - Car Rental Service</title>
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

<!-- Payment History -->
<div class="container mt-4">
    <h1 class="mb-4">Payment History</h1>

    <!-- Payment History Table -->
    <div class="card">
        <div class="card-body">
            <c:choose>
                <c:when test="${empty bookings}">
                    <div class="text-center py-5">
                        <h5>No payment history found</h5>
                        <p>You haven't made any payments yet.</p>
                        <a href="<c:url value='/vehicles/available'/>" class="btn btn-primary mt-3">Browse Available Vehicles</a>
                    </div>
                </c:when>
                <c:otherwise>
                    <!-- Initialize paymentsByBookingId if it's null -->
                    <c:if test="${empty paymentsByBookingId}">
                        <c:set var="paymentsByBookingId" value="${{}}" scope="request"/>
                    </c:if>

                    <div class="table-responsive">
                        <table class="table table-striped table-hover">
                            <thead>
                            <tr>
                                <th>Booking #</th>
                                <th>Vehicle</th>
                                <th>Rental Period</th>
                                <th>Payment Date</th>
                                <th>Amount</th>
                                <th>Method</th>
                                <th>Status</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${bookings}" var="booking">
                                <c:set var="bookingPayments" value="${paymentsByBookingId[booking.bookingId]}" />

                                <c:choose>
                                    <c:when test="${not empty bookingPayments}">
                                        <c:forEach items="${bookingPayments}" var="payment">
                                            <tr>
                                                <td>
                                                    <a href="<c:url value='/bookings/details/${booking.bookingId}'/>">${booking.bookingId}</a>
                                                </td>
                                                <td>${booking.vehicleId}</td>
                                                <td>${booking.startDate} to ${booking.endDate}</td>
                                                <td>
                                                    <fmt:parseDate value="${payment.paymentDate}" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDate" />
                                                    <fmt:formatDate value="${parsedDate}" pattern="MMM dd, yyyy" />
                                                </td>
                                                <td>$${payment.amount}</td>
                                                <td>${payment.paymentMethod}</td>
                                                <td>
                                                    <span class="badge ${payment.status == 'completed' ? 'bg-success' : payment.status == 'refunded' ? 'bg-danger' : 'bg-secondary'}">
                                                            ${payment.status}
                                                    </span>
                                                </td>
                                                <td>
                                                    <a href="<c:url value='/payments/receipt/${payment.paymentId}'/>" class="btn btn-sm btn-info">View Receipt</a>
                                                </td>
                                            </tr>
                                        </c:forEach>
                                    </c:when>
                                    <c:when test="${booking.status == 'pending'}">
                                        <tr>
                                            <td>
                                                <a href="<c:url value='/bookings/details/${booking.bookingId}'/>">${booking.bookingId}</a>
                                            </td>
                                            <td>${booking.vehicleId}</td>
                                            <td>${booking.startDate} to ${booking.endDate}</td>
                                            <td>-</td>
                                            <td>$${booking.totalCost}</td>
                                            <td>-</td>
                                            <td>
                                                <span class="badge bg-warning">Pending Payment</span>
                                            </td>
                                            <td>
                                                <a href="<c:url value='/payments/process/${booking.bookingId}'/>" class="btn btn-sm btn-success">Pay Now</a>
                                            </td>
                                        </tr>
                                    </c:when>
                                    <c:otherwise>
                                        <!-- No payments found but not pending -->
                                        <tr>
                                            <td><a href="<c:url value='/bookings/details/${booking.bookingId}'/>">${booking.bookingId}</a></td>
                                            <td>${booking.vehicleId}</td>
                                            <td>${booking.startDate} to ${booking.endDate}</td>
                                            <td>-</td>
                                            <td>$${booking.totalCost}</td>
                                            <td>-</td>
                                            <td><span class="badge bg-secondary">${booking.status}</span></td>
                                            <td>-</td>
                                        </tr>
                                    </c:otherwise>
                                </c:choose>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </c:otherwise>
            </c:choose>
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