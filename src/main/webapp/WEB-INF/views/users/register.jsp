<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Register - Car Rental Service</title>
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
      </ul>
      <ul class="navbar-nav">
        <li class="nav-item">
          <a class="nav-link" href="<c:url value='/users/login'/>">Login</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="<c:url value='/users/register'/>">Register</a>
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

<!-- Registration Form -->
<div class="container mt-4">
  <div class="row">
    <div class="col-md-8 offset-md-2">
      <div class="card">
        <div class="card-header">
          <h4 class="mb-0">Create an Account</h4>
        </div>
        <div class="card-body">
          <form action="<c:url value='/users/register'/>" method="post" class="needs-validation" novalidate>
            <div class="row mb-3">
              <div class="col-md-6">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
                <div class="invalid-feedback">
                  Please choose a username.
                </div>
              </div>
              <div class="col-md-6">
                <label for="email" class="form-label">Email Address</label>
                <input type="email" class="form-control" id="email" name="email" required>
                <div class="invalid-feedback">
                  Please enter a valid email address.
                </div>
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <label for="password" class="form-label">Password</label>
                <input type="password" class="form-control" id="password" name="password" required>
                <div class="invalid-feedback">
                  Please enter a password.
                </div>
              </div>
              <div class="col-md-6">
                <label for="phoneNumber" class="form-label">Phone Number</label>
                <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" required>
                <div class="invalid-feedback">
                  Please enter your phone number.
                </div>
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <label for="licenseNumber" class="form-label">Driver's License Number</label>
                <input type="text" class="form-control" id="licenseNumber" name="licenseNumber" required>
                <div class="invalid-feedback">
                  Please enter your driver's license number.
                </div>
              </div>
              <div class="col-md-6">
                <label for="licenseExpiry" class="form-label">License Expiry Date</label>
                <input type="date" class="form-control" id="licenseExpiry" name="licenseExpiry" required>
                <div class="invalid-feedback">
                  Please enter your license expiry date.
                </div>
              </div>
            </div>

            <div class="mb-3">
              <label for="membershipType" class="form-label">Membership Type</label>
              <select class="form-select" id="membershipType" name="membershipType" required>
                <option value="" selected disabled>Select membership type</option>
                <option value="regular">Regular</option>
                <option value="premium">Premium</option>
              </select>
              <div class="invalid-feedback">
                Please select a membership type.
              </div>
            </div>

            <div class="mb-3 form-check">
              <input type="checkbox" class="form-check-input" id="terms" required>
              <label class="form-check-label" for="terms">I agree to the terms and conditions</label>
              <div class="invalid-feedback">
                You must agree before submitting.
              </div>
            </div>

            <div class="d-grid gap-2">
              <button type="submit" class="btn btn-primary">Register</button>
            </div>
          </form>
        </div>
        <div class="card-footer text-center">
          <p class="mb-0">Already have an account? <a href="<c:url value='/users/login'/>">Login here</a></p>
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