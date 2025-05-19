<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>My Profile - Car Rental Service</title>
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
          <a class="nav-link active" href="<c:url value='/users/profile'/>">Profile</a>
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

<!-- Profile Content -->
<div class="container mt-4">
  <div class="row">
    <div class="col-md-8 offset-md-2">
      <div class="card">
        <div class="card-header d-flex justify-content-between align-items-center">
          <h4 class="mb-0">My Profile</h4>
          <a href="<c:url value='/users/edit'/>" class="btn btn-primary btn-sm">Edit Profile</a>
        </div>
        <div class="card-body">
          <div class="row mb-4">
            <div class="col-md-6">
              <h5>Account Information</h5>
              <table class="table table-bordered">
                <tbody>
                <tr>
                  <th style="width: 40%">Username</th>
                  <td>${user.username}</td>
                </tr>
                <tr>
                  <th>Email</th>
                  <td>${user.email}</td>
                </tr>
                <tr>
                  <th>Phone</th>
                  <td>${user.phoneNumber}</td>
                </tr>
                </tbody>
              </table>
            </div>

            <c:if test="${user['class'].simpleName == 'CustomerUser'}">
              <div class="col-md-6">
                <h5>Driver Information</h5>
                <table class="table table-bordered">
                  <tbody>
                  <tr>
                    <th style="width: 40%">License Number</th>
                    <td>${user.licenseNumber}</td>
                  </tr>
                  <tr>
                    <th>License Expiry</th>
                    <td>${user.licenseExpiry}</td>
                  </tr>
                  <tr>
                    <th>Membership</th>
                    <td>
                                                    <span class="badge ${user.membershipType == 'premium' ? 'bg-success' : 'bg-primary'}">
                                                        ${user.membershipType}
                                                    </span>
                    </td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </c:if>

            <c:if test="${user['class'].simpleName == 'AdminUser'}">
              <div class="col-md-6">
                <h5>Admin Information</h5>
                <table class="table table-bordered">
                  <tbody>
                  <tr>
                    <th style="width: 40%">Admin ID</th>
                    <td>${user.adminId}</td>
                  </tr>
                  <tr>
                    <th>Role</th>
                    <td>${user.role}</td>
                  </tr>
                  </tbody>
                </table>
              </div>
            </c:if>
          </div>

          <div class="row">
            <div class="col-12">
              <h5>Account Actions</h5>
              <div class="list-group">
                <a href="<c:url value='/users/edit'/>" class="list-group-item list-group-item-action">
                  Update Profile Information
                </a>
                <a href="<c:url value='/bookings/list'/>" class="list-group-item list-group-item-action">
                  View My Bookings
                </a>
                <a href="<c:url value='/payments/history'/>" class="list-group-item list-group-item-action">
                  View Payment History
                </a>
              </div>
            </div>
          </div>
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