<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Dashboard - Car Rental Service</title>
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
          <a class="nav-link active" href="<c:url value='/dashboard'/>">Dashboard</a>
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

<!-- Dashboard Content -->
<div class="container mt-4">
  <h1>Welcome, ${loggedInUser.username}</h1>
  <div class="row mt-4">
    <!-- Quick Actions -->
    <div class="col-md-4 mb-4">
      <div class="card">
        <div class="card-header">Quick Actions</div>
        <div class="card-body quick-actions">
          <a href="<c:url value='/vehicles/available'/>" class="btn btn-primary w-100 mb-2">Rent a Vehicle</a>
          <a href="<c:url value='/bookings/list'/>" class="btn btn-outline-primary w-100 mb-2">My Bookings</a>
          <a href="<c:url value='/rental-records/list'/>" class="btn btn-success w-100 mb-2">
            <i class="bi bi-link"></i> Rental Records (Linked List)
          </a>
          <a href="<c:url value='/payments/history'/>" class="btn btn-outline-primary w-100 mb-2">Payment History</a>
          <a href="<c:url value='/users/profile'/>" class="btn btn-outline-primary w-100">My Profile</a>
        </div>
      </div>
    </div>
    <!-- Active Rentals -->
    <div class="col-md-8 mb-4">
      <div class="card">
        <div class="card-header">Active Rentals</div>
        <div class="card-body">
          <c:choose>
            <c:when test="${empty bookings}">
              <p class="text-center">You don't have any active rentals.</p>
              <div class="text-center mt-3">
                <a href="<c:url value='/vehicles/available'/>" class="btn btn-primary">Rent a Vehicle Now</a>
              </div>
            </c:when>
            <c:otherwise>
              <div class="table-responsive">
                <table class="table table-hover">
                  <thead>
                  <tr>
                    <th>Booking ID</th>
                    <th>Vehicle</th>
                    <th>Dates</th>
                    <th>Status</th>
                    <th>Actions</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${bookings}" var="booking">
                    <c:if test="${booking.status == 'active' || booking.status == 'pending'}">
                      <tr>
                        <td>${booking.bookingId}</td>
                        <td>${booking.vehicleId}</td>
                        <td>${booking.startDate} to ${booking.endDate}</td>
                        <td>
                                                            <span class="badge ${booking.status == 'active' ? 'bg-success' : 'bg-warning'}">
                                                                ${booking.status}
                                                            </span>
                        </td>
                        <td>
                          <a href="<c:url value='/bookings/details/${booking.bookingId}'/>" class="btn btn-sm btn-info">Details</a>
                          <a href="<c:url value='/bookings/cancel/${booking.bookingId}'/>" class="btn btn-sm btn-danger" onclick="return confirm('Are you sure you want to cancel this booking?')">Cancel</a>
                        </td>
                      </tr>
                    </c:if>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </div>
    <div class="card mb-4">
      <div class="card-header bg-primary text-white">
        <i class="bi bi-sort-down"></i> Vehicle Sorting Options
      </div>
      <div class="card-body p-3">
        <div class="row g-2">
          <div class="col-12">
            <span class="badge bg-secondary mb-2">Selection Sort Algorithm</span>
            <p class="small text-muted mb-2">View vehicles sorted in different ways:</p>
          </div>
          <div class="col-6">
            <a href="<c:url value='/vehicles/list?sortBy=price'/>" class="btn btn-outline-primary w-100">
              <i class="bi bi-arrow-up"></i> Price: Low to High
            </a>
          </div>
          <div class="col-6">
            <a href="<c:url value='/vehicles/list?sortBy=price&order=desc'/>" class="btn btn-outline-primary w-100">
              <i class="bi bi-arrow-down"></i> Price: High to Low
            </a>
          </div>
          <div class="col-6 mt-2">
            <a href="<c:url value='/vehicles/list?sortBy=availability'/>" class="btn btn-outline-primary w-100">
              <i class="bi bi-check-circle"></i> Available First
            </a>
          </div>
          <div class="col-6 mt-2">
            <a href="<c:url value='/vehicles/list'/>" class="btn btn-primary w-100">
              <i class="bi bi-sort"></i> All Sorting Options
            </a>
          </div>
        </div>
      </div>
    </div>


  <!-- Recent Activity -->
  <div class="row">
    <div class="col-12 mb-4">
      <div class="card">
        <div class="card-header">Recent Activity</div>
        <div class="card-body">
          <c:choose>
            <c:when test="${empty bookings}">
              <p class="text-center">No recent activity to display.</p>
            </c:when>
            <c:otherwise>
              <div class="table-responsive">
                <table class="table table-hover">
                  <thead>
                  <tr>
                    <th>Date</th>
                    <th>Activity</th>
                    <th>Details</th>
                  </tr>
                  </thead>
                  <tbody>
                  <c:forEach items="${bookings}" var="booking" end="4">
                    <tr>
                      <td>${booking.startDate}</td>
                      <td>Booking ${booking.status}</td>
                      <td>
                        <a href="<c:url value='/bookings/details/${booking.bookingId}'/>">
                          View Booking #${booking.bookingId}
                        </a>
                      </td>
                    </tr>
                  </c:forEach>
                  </tbody>
                </table>
              </div>
            </c:otherwise>
          </c:choose>
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