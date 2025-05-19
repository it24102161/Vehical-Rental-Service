<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Car Rental Service</title>
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
                    <a class="nav-link active" href="<c:url value='/'/>">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/vehicles/available'/>">Available Vehicles</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/dashboard'/>">Dashboard</a>
                </li>
            </ul>
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${empty loggedInUser}">
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/users/login'/>">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/users/register'/>">Register</a>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/users/profile'/>">Profile</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='/users/logout'/>">Logout</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<!-- Hero Section -->
<div class="container mt-4">
    <div class="row">
        <div class="col-md-8 offset-md-2 text-center">
            <h1 class="display-4">Welcome to Car Rental Service</h1>
            <p class="lead">Your journey starts with choosing the right vehicle</p>
            <a href="<c:url value='/vehicles/available'/>" class="btn btn-primary btn-lg mt-3">Browse Available Vehicles</a>
        </div>
    </div>
</div>

<!-- Featured Vehicles -->
<div class="container mt-5">
    <h2 class="text-center mb-4">Featured Vehicles</h2>
    <div class="row">
        <div class="col-md-4 mb-4">
            <div class="card vehicle-card">
                <div class="card-body">
                    <h5 class="card-title">Economy Cars</h5>
                    <p class="card-text">Affordable and fuel-efficient options for budget-conscious travelers.</p>
                    <a href="<c:url value='/vehicles/available'/>" class="btn btn-outline-primary">View Options</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="card vehicle-card">
                <div class="card-body">
                    <h5 class="card-title">Luxury Sedans</h5>
                    <p class="card-text">Travel in style with our premium sedan options with advanced features.</p>
                    <a href="<c:url value='/vehicles/available'/>" class="btn btn-outline-primary">View Options</a>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="card vehicle-card">
                <div class="card-body">
                    <h5 class="card-title">SUVs & Crossovers</h5>
                    <p class="card-text">Spacious vehicles perfect for family trips and outdoor adventures.</p>
                    <a href="<c:url value='/vehicles/available'/>" class="btn btn-outline-primary">View Options</a>
                </div>
            </div>
        </div>
    </div>
</div>

<!-- How It Works -->
<div class="container mt-5">
    <h2 class="text-center mb-4">How It Works</h2>
    <div class="row text-center">
        <div class="col-md-4 mb-4">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">1. Choose a Vehicle</h5>
                    <p class="card-text">Browse our collection of vehicles and select the one that fits your needs.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">2. Book Online</h5>
                    <p class="card-text">Complete your reservation with our easy booking system in minutes.</p>
                </div>
            </div>
        </div>
        <div class="col-md-4 mb-4">
            <div class="card h-100">
                <div class="card-body">
                    <h5 class="card-title">3. Enjoy Your Trip</h5>
                    <p class="card-text">Pick up your vehicle and start your journey with confidence.</p>
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