package com.example.carrental.reviewapp;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Review implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private String name;
    private String vehicle;
    private int rating;
    private String comments;
    private boolean isVerified;
    private String reviewId;
    private String id;
    private String status = "pending"; // "approved", "pending", "rejected"
    private LocalDateTime createdAt;

    public Review(String name, String vehicle, int rating, String comments) {
        this.name = name;
        this.vehicle = vehicle;
        this.rating = Math.max(1, Math.min(5, rating));
        this.comments = comments;
        this.isVerified = false;
        this.reviewId = java.util.UUID.randomUUID().toString();
        this.createdAt = LocalDateTime.now();
    }

    // Getters and Setters
    public String getName() { return name; }
    public String getVehicle() { return vehicle; }
    public int getRating() { return rating; }
    public String getComments() { return comments; }
    public boolean isVerified() { return isVerified; }
    public String getReviewId() { return reviewId; }

    public void setName(String name) { this.name = name; }
    public void setVehicle(String vehicle) { this.vehicle = vehicle; }
    public void setRating(int rating) { this.rating = rating; }
    public void setComments(String comments) { this.comments = comments; }
    public void setVerified(boolean verified) { isVerified = verified; }
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}