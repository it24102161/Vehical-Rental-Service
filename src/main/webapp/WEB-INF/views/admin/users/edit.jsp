<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit User - Admin Dashboard</title>
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
                <div class="card-header d-flex justify-content-between align-items-center">
                    <h4 class="mb-0">Edit User</h4>
                    <span class="badge ${user['class'].simpleName == 'AdminUser' ? 'bg-primary' : 'bg-success'}">
                        ${user['class'].simpleName == 'AdminUser' ? 'Admin' : 'Customer'}
                    </span>
                </div>
                <div class="card-body">
                    <!-- CustomerUser Form -->
                    <c:if test="${user['class'].simpleName == 'CustomerUser'}">
                        <form action="<c:url value='/users/admin/edit/${user.userId}'/>" method="post" class="needs-validation" novalidate>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
                                    <div class="invalid-feedback">
                                        Please enter a username.
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="email" class="form-label">Email Address</label>
                                    <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
                                    <div class="invalid-feedback">
                                        Please enter a valid email address.
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="password" class="form-label">Password (leave blank to keep current)</label>
                                    <input type="password" class="form-control" id="password" name="password">
                                </div>
                                <div class="col-md-6">
                                    <label for="phoneNumber" class="form-label">Phone Number</label>
                                    <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" required>
                                    <div class="invalid-feedback">
                                        Please enter a phone number.
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="licenseNumber" class="form-label">License Number</label>
                                    <input type="text" class="form-control" id="licenseNumber" name="licenseNumber" value="${user.licenseNumber}" required>
                                    <div class="invalid-feedback">
                                        Please enter a license number.
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="licenseExpiry" class="form-label">License Expiry</label>
                                    <input type="date" class="form-control" id="licenseExpiry" name="licenseExpiry" value="${user.licenseExpiry}" required>
                                    <div class="invalid-feedback">
                                        Please enter the license expiry date.
                                    </div>
                                </div>
                            </div>

                            <div class="mb-3">
                                <label for="membershipType" class="form-label">Membership Type</label>
                                <select class="form-select" id="membershipType" name="membershipType" required>
                                    <option value="regular" ${user.membershipType == 'regular' ? 'selected' : ''}>Regular</option>
                                    <option value="premium" ${user.membershipType == 'premium' ? 'selected' : ''}>Premium</option>
                                </select>
                                <div class="invalid-feedback">
                                    Please select a membership type.
                                </div>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Update User</button>
                                <a href="<c:url value='/users/admin/list'/>" class="btn btn-outline-secondary">Cancel</a>
                            </div>
                        </form>
                    </c:if>

                    <!-- AdminUser Form -->
                    <c:if test="${user['class'].simpleName == 'AdminUser'}">
                        <form action="<c:url value='/users/admin/edit/${user.userId}'/>" method="post" class="needs-validation" novalidate>
                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="username" class="form-label">Username</label>
                                    <input type="text" class="form-control" id="username" name="username" value="${user.username}" required>
                                    <div class="invalid-feedback">
                                        Please enter a username.
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="email" class="form-label">Email Address</label>
                                    <input type="email" class="form-control" id="email" name="email" value="${user.email}" required>
                                    <div class="invalid-feedback">
                                        Please enter a valid email address.
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="password" class="form-label">Password (leave blank to keep current)</label>
                                    <input type="password" class="form-control" id="password" name="password">
                                </div>
                                <div class="col-md-6">
                                    <label for="phoneNumber" class="form-label">Phone Number</label>
                                    <input type="tel" class="form-control" id="phoneNumber" name="phoneNumber" value="${user.phoneNumber}" required>
                                    <div class="invalid-feedback">
                                        Please enter a phone number.
                                    </div>
                                </div>
                            </div>

                            <div class="row mb-3">
                                <div class="col-md-6">
                                    <label for="adminId" class="form-label">Admin ID</label>
                                    <input type="text" class="form-control" id="adminId" name="adminId" value="${user.adminId}" required>
                                    <div class="invalid-feedback">
                                        Please enter an admin ID.
                                    </div>
                                </div>
                                <div class="col-md-6">
                                    <label for="role" class="form-label">Role</label>
                                    <input type="text" class="form-control" id="role" name="role" value="${user.role}" required>
                                    <div class="invalid-feedback">
                                        Please enter a role.
                                    </div>
                                </div>
                            </div>

                            <div class="d-grid gap-2">
                                <button type="submit" class="btn btn-primary">Update Admin</button>
                                <a href="<c:url value='/users/admin/list'/>" class="btn btn-outline-secondary">Cancel</a>
                            </div>
                        </form>
                    </c:if>
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