package com.example.carrental.service;

import com.example.carrental.model.Review;
import com.example.carrental.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ReviewService {
    private final ReviewRepository repository;

    @Autowired
    public ReviewService(ReviewRepository repository) {
        this.repository = repository;
    }

    public void saveReview(Review review) {
        repository.save(review);
    }

    public List<Review> getReviewsByVehicleId(String vehicleId) {
        return repository.findByVehicleId(vehicleId);
    }

    public List<Review> getAllReviews() {
        return repository.findAll();
    }

    public void deleteReview(Long id) {
        repository.deleteById(id);
    }

    public Review getReview(Long id) {
        return repository.findById(id);
    }
}
