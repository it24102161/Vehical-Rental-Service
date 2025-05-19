<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Manage Users - Admin Dashboard</title>
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
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h1>User Management</h1>
        <a href="<c:url value='/users/admin/register'/>" class="btn btn-success">
            <i class="bi bi-plus-lg"></i> Add New Admin
        </a>
    </div>

    <!-- Filter Form -->
    <div class="card mb-4">
        <div class="card-body">
            <form action="<c:url value='/users/admin/list'/>" method="get" class="row g-3">
                <div class="col-md-4">
                    <label for="userType" class="form-label">User Type</label>
                    <select class="form-select" id="userType" name="userType">
                        <option value="">All Users</option>
                        <option value="customer">Customers</option>
                        <option value="admin">Admins</option>
                    </select>
                </div>
                <div class="col-md-4">
                    <label for="searchTerm" class="form-label">Search</label>
                    <input type="text" class="form-control" id="searchTerm" name="searchTerm" placeholder="Username, Email, etc.">
                </div>
                <div class="col-md-4 d-flex align-items-end">
                    <button type="submit" class="btn btn-primary w-100">Filter</button>
                </div>
            </form>
        </div>
    </div>

    <!-- Users Table -->
    <div class="card">
        <div class="card-body">
            <div class="table-responsive">
                <table class="table table-striped table-hover">
                    <thead>
                    <tr>
                        <th>ID</th>
                        <th>Username</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>User Type</th>
                        <th>Additional Info</th>
                        <th>Actions</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:choose>
                        <c:when test="${empty users}">
                            <tr>
                                <td colspan="7" class="text-center">No users found</td>
                            </tr>
                        </c:when>
                        <c:otherwise>
                            <c:forEach items="${users}" var="user">
                                <tr>
                                    <td>${user.userId}</td>
                                    <td>${user.username}</td>
                                    <td>${user.email}</td>
                                    <td>${user.phoneNumber}</td>
                                    <td>
                                        <c:choose>
                                            <c:when test="${user['class'].simpleName == 'AdminUser'}">
                                                <span class="badge bg-primary">Admin</span>
                                            </c:when>
                                            <c:when test="${user['class'].simpleName == 'CustomerUser'}">
                                                <span class="badge bg-success">Customer</span>
                                            </c:when>
                                            <c:otherwise>
                                                <span class="badge bg-secondary">User</span>
                                            </c:otherwise>
                                        </c:choose>
                                    </td>
                                    <td>
                                        <c:if test="${user['class'].simpleName == 'CustomerUser'}">
                                            <span class="badge bg-info">${user.membershipType}</span>
                                        </c:if>
                                        <c:if test="${user['class'].simpleName == 'AdminUser'}">
                                            <span class="badge bg-dark">${user.role}</span>
                                        </c:if>
                                    </td>
                                    <td>
                                        <div class="btn-group" role="group">
                                            <a href="<c:url value='/users/admin/edit/${user.userId}'/>" class="btn btn-sm btn-warning">
                                                <i class="bi bi-pencil"></i>
                                            </a>
                                            <a href="<c:url value='/users/admin/delete/${user.userId}'/>" class="btn btn-sm btn-danger"
                                               onclick="return confirm('Are you sure you want to delete this user?')">
                                                <i class="bi bi-trash"></i>
                                            </a>
                                        </div>
                                    </td>
                                </tr>
                            </c:forEach>
                        </c:otherwise>
                    </c:choose>
                    </tbody>
                </table>
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