<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Dashboard - Car Rental Service</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet">
  <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.8.1/font/bootstrap-icons.css">
  <link rel="stylesheet" href="<c:url value='/static/admin-styles.css'/>">
</head>
<body>
<!-- Navigation -->
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
  <div class="container">
    <a class="navbar-brand" href="<c:url value='/admin/dashboard'/>">Admin Dashboard</a>
    <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
      <span class="navbar-toggler-icon"></span>
    </button>
    <div class="collapse navbar-collapse" id="navbarNav">
      <ul class="navbar-nav me-auto">
        <li class="nav-item">
          <a class="nav-link active" href="<c:url value='/admin/dashboard'/>">Dashboard</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/users/admin/list'/>">Users</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/vehicles/admin/list'/>">Vehicles</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/bookings/admin/list'/>">Bookings</a>
        </li>
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/payments/admin/list'/>">Payments</a>
        </li>
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/'/>">Main Site</a>
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
  <h1>Admin Dashboard</h1>
  <p>Welcome, ${loggedInUser.username}</p>

  <!-- Stats Overview -->
  <div class="row mt-4">
    <div class="col-md-3 mb-4">
      <div class="card stats-card">
        <div class="card-body">
          <div class="number">${userCount}</div>
          <div class="label">Total Users</div>
        </div>
      </div>
    </div>
    <div class="col-md-3 mb-4">
      <div class="card stats-card">
        <div class="card-body">
          <div class="number">${vehicleCount}</div>
          <div class="label">Total Vehicles</div>
        </div>
      </div>
    </div>
    <div class="col-md-3 mb-4">
      <div class="card stats-card">
        <div class="card-body">
          <div class="number">${activeBookingsCount}</div>
          <div class="label">Active Bookings</div>
        </div>
      </div>
    </div>
    <div class="col-md-3 mb-4">
      <div class="card stats-card">
        <div class="card-body">
          <div class="number">${revenue}</div>
          <div class="label">Total Revenue</div>
        </div>
      </div>
    </div>
  </div>

  <!-- Quick Actions -->
  <!-- Quick Actions -->
  <div class="row mt-2">
    <div class="col-md-3 mb-4">
      <div class="card">
        <div class="card-header bg-primary text-white">
          <i class="bi bi-people"></i> User Management
        </div>
        <div class="card-body">
          <a href="<c:url value='/users/admin/register'/>" class="btn btn-outline-primary w-100 mb-2">Add Admin</a>
          <a href="<c:url value='/users/admin/list'/>" class="btn btn-outline-primary w-100">View All Users</a>
        </div>
      </div>
    </div>
    <div class="col-md-3 mb-4">
      <div class="card">
        <div class="card-header bg-success text-white">
          <i class="bi bi-car-front"></i> Vehicle Management
        </div>
        <div class="card-body">
          <a href="<c:url value='/vehicles/admin/add'/>" class="btn btn-outline-success w-100 mb-2">Add Vehicle</a>
          <a href="<c:url value='/vehicles/admin/list'/>" class="btn btn-outline-success w-100">View All Vehicles</a>
        </div>
      </div>
    </div>
    <div class="col-md-3 mb-4">
      <div class="card">
        <div class="card-header bg-info text-white">
          <i class="bi bi-calendar-check"></i> Booking Management
        </div>
        <div class="card-body">
          <a href="<c:url value='/bookings/admin/list'/>" class="btn btn-outline-info w-100">View All Bookings</a>
        </div>
      </div>
    </div>
    <div class="col-md-3 mb-4">
      <div class="card">
        <div class="card-header bg-warning text-dark">
          <i class="bi bi-credit-card"></i> Payment Management
        </div>
        <div class="card-body">
          <a href="<c:url value='/payments/admin/list'/>" class="btn btn-outline-warning w-100">View All Payments</a>
        </div>
      </div>
    </div>
  </div>

  <!-- Linked List Section -->
  <div class="row mt-2">
    <div class="col-12 mb-4">
      <div class="card">
        <div class="card-header bg-danger text-white">
          <i class="bi bi-link-45deg"></i> Rental Records (Linked List Implementation)
        </div>
        <div class="card-body">
          <div class="row">
            <div class="col-md-8">
              <p><strong>Linked List Data Structure:</strong> View rental records stored in our custom linked list implementation. Each rental record is a node with references to the next record, allowing efficient traversal and memory management.</p>
            </div>
            <div class="col-md-4 text-end">
              <a href="<c:url value='/rental-records/admin/list'/>" class="btn btn-danger w-100">
                <i class="bi bi-link"></i> View Rental Records List
              </a>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>

  <!-- Recent Activity Table -->
  <div class="row mt-2">
    <div class="col-12">
      <div class="card">
        <div class="card-header">Recent Bookings</div>
        <div class="card-body">
          <div class="table-responsive">
            <table class="table table-hover">
              <thead>
              <tr>
                <th>Booking ID</th>
                <th>User</th>
                <th>Vehicle</th>
                <th>Dates</th>
                <th>Status</th>
                <th>Total</th>
                <th>Actions</th>
              </tr>
              </thead>
              <tbody>
              <c:forEach items="${recentBookings}" var="booking" end="9">
                <tr>
                  <td>${booking.bookingId}</td>
                  <td>${booking.userId}</td>
                  <td>${booking.vehicleId}</td>
                  <td>${booking.startDate} to ${booking.endDate}</td>
                  <td>
                                                <span class="badge ${booking.status == 'active' ? 'bg-success' : booking.status == 'pending' ? 'bg-warning' : booking.status == 'completed' ? 'bg-info' : 'bg-danger'}">
                                                    ${booking.status}
                                                </span>
                  </td>
                  <td>$${booking.totalCost}</td>
                  <td>
                    <a href="<c:url value='/bookings/details/${booking.bookingId}'/>" class="btn btn-sm btn-info">Details</a>
                    <c:if test="${booking.status == 'active'}">
                      <a href="<c:url value='/bookings/admin/complete/${booking.bookingId}'/>" class="btn btn-sm btn-success">Complete</a>
                    </c:if>
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </div>
          <div class="text-end mt-3">
            <a href="<c:url value='/bookings/admin/list'/>" class="btn btn-primary">View All Bookings</a>
          </div>
        </div>
      </div>
    </div>
  </div>
</div>

<!-- Footer -->
<footer class="bg-dark text-white mt-5 py-3">
  <div class="container text-center">
    <p class="mb-0">&copy; 2025 Car Rental Service. Admin Dashboard.</p>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value='/static/admin-scripts.js'/>"></script>
</body>
</html>