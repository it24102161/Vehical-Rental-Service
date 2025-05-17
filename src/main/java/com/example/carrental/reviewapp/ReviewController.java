package com.example.carrental.reviewapp;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.io.*;
import java.nio.file.*;

@RestController
@RequestMapping("/reviews")
public class ReviewController {
    private final List<Review> reviews = new ArrayList<>();
    private static final String REVIEWS_FILE = "reviews.txt";

    public ReviewController() {
        loadReviewsFromFile();
    }

    // Create
    @PostMapping
    public String submitReview(@RequestParam String name,
                               @RequestParam String vehicle,
                               @RequestParam int rating,
                               @RequestParam String comments) {
        Review review = new Review(name, vehicle, rating, comments);
        reviews.add(review);
        saveReviewsToFile();
        return "Review submitted successfully!";
    }

    // Read all
    @GetMapping
    public List<Review> getAllReviews() {
        return reviews;
    }

    // Read by vehicle
    @GetMapping("/vehicle/{vehicle}")
    public List<Review> getReviewsByVehicle(@PathVariable String vehicle) {
        return reviews.stream()
                .filter(r -> r.getVehicle().equalsIgnoreCase(vehicle))
                .toList();
    }

    // Update
    @PutMapping("/{reviewId}")
    public String updateReview(@PathVariable String reviewId,
                               @RequestParam(required = false) String name,
                               @RequestParam(required = false) String comments,
                               @RequestParam(required = false) Integer rating) {
        Optional<Review> reviewOpt = reviews.stream()
                .filter(r -> r.getReviewId().equals(reviewId))
                .findFirst();

        if (reviewOpt.isPresent()) {
            Review review = reviewOpt.get();
            if (name != null) review.setName(name);
            if (comments != null) review.setComments(comments);
            if (rating != null) review.setRating(rating);
            saveReviewsToFile();
            return "Review updated successfully!";
        }
        return "Review not found!";
    }

    // Delete
    @DeleteMapping("/{reviewId}")
    public String deleteReview(@PathVariable String reviewId) {
        boolean removed = reviews.removeIf(r -> r.getReviewId().equals(reviewId));
        if (removed) {
            saveReviewsToFile();
            return "Review deleted successfully!";
        }
        return "Review not found!";
    }

    // Admin endpoint to verify a review
    @PostMapping("/verify/{reviewId}")
    public String verifyReview(@PathVariable String reviewId,
                               @RequestParam String verificationMethod) {
        Optional<Review> reviewOpt = reviews.stream()
                .filter(r -> r.getReviewId().equals(reviewId))
                .findFirst();

        if (reviewOpt.isPresent()) {
            Review oldReview = reviewOpt.get();
            VerifiedReview verifiedReview = new VerifiedReview(
                    oldReview.getName(),
                    oldReview.getVehicle(),
                    oldReview.getRating(),
                    oldReview.getComments(),
                    verificationMethod);

            reviews.remove(oldReview);
            reviews.add(verifiedReview);
            saveReviewsToFile();
            return "Review verified successfully!";
        }
        return "Review not found!";
    }

    private void saveReviewsToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(REVIEWS_FILE))) {
            oos.writeObject(reviews);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private void loadReviewsFromFile() {
        if (Files.exists(Paths.get(REVIEWS_FILE))) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(REVIEWS_FILE))) {
                Object obj = ois.readObject();
                if (obj instanceof List) {
                    reviews.addAll((List<Review>) obj);
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }
}