<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Car Rental System</title>
    <!-- Bootstrap CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Custom CSS -->
    <link href="<c:url value='//css/styles.css' />" rel="stylesheet">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="<c:url value='/' />">Car Rental</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='/' />">Home</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link" href="<c:url value='//vehicles/list' />">Vehicles</a>
                </li>
                <c:if test="${not empty loggedInUser}">
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='//dashboard' />">Dashboard</a>
                    </li>
                    <li class="nav-item">
                        <a class="nav-link" href="<c:url value='//bookings/list' />">My Bookings</a>
                    </li>
                </c:if>
            </ul>
            <ul class="navbar-nav">
                <c:choose>
                    <c:when test="${not empty loggedInUser}">
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" id="navbarDropdown" role="button"
                               data-bs-toggle="dropdown" aria-expanded="false">
                                    ${loggedInUser.username}
                            </a>
                            <ul class="dropdown-menu dropdown-menu-end" aria-labelledby="navbarDropdown">
                                <li><a class="dropdown-item" href="<c:url value='//users/profile' />">Profile</a></li>
                                <c:if test="${loggedInUser['class'].simpleName eq 'AdminUser'}">
                                    <li><a class="dropdown-item" href="<c:url value='//admin/dashboard' />">Admin Dashboard</a></li>
                                </c:if>
                                <li><hr class="dropdown-divider"></li>
                                <li><a class="dropdown-item" href="<c:url value='//users/logout' />">Logout</a></li>
                            </ul>
                        </li>
                    </c:when>
                    <c:otherwise>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='//users/login' />">Login</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="<c:url value='//users/register' />">Register</a>
                        </li>
                    </c:otherwise>
                </c:choose>
            </ul>
        </div>
    </div>
</nav>

<div class="container mt-4">
    <!-- Display flash messages -->
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
