<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Add Admin User - Admin Dashboard</title>
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
          <a class="nav-link" href="<c:url value='/admin/dashboard'/>">Dashboard</a>
        </li>
        <li class="nav-item">
          <a class="nav-link active" href="<c:url value='/users/admin/list'/>">Users</a>
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

<!-- Content -->
<div class="container mt-4">
  <div class="row">
    <div class="col-md-8 offset-md-2">
      <div class="card">
        <div class="card-header">
          <h4 class="mb-0">Add Admin User</h4>
        </div>
        <div class="card-body">
          <form action="<c:url value='/users/admin/register'/>" method="post" class="needs-validation" novalidate>
            <div class="row mb-3">
              <div class="col-md-6">
                <label for="username" class="form-label">Username</label>
                <input type="text" class="form-control" id="username" name="username" required>
                <div class="invalid-feedback">
                  Please enter a username.
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
                  Please enter a phone number.
                </div>
              </div>
            </div>

            <div class="row mb-3">
              <div class="col-md-6">
                <label for="adminId" class="form-label">Admin ID</label>
                <input type="text" class="form-control" id="adminId" name="adminId" required>
                <div class="invalid-feedback">
                  Please enter an admin ID.
                </div>
              </div>
              <div class="col-md-6">
                <label for="role" class="form-label">Admin Role</label>
                <input type="text" class="form-control" id="role" name="role" value="admin" readonly>
              </div>
            </div>

            <div class="d-grid gap-2">
              <button type="submit" class="btn btn-primary">Register Admin</button>
              <a href="<c:url value='/users/admin/list'/>" class="btn btn-outline-secondary">Cancel</a>
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
    <p class="mb-0">&copy; 2025 Car Rental Service. Admin Dashboard.</p>
  </div>
</footer>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
<script src="<c:url value='/static/admin-scripts.js'/>"></script>
</body>
</html>