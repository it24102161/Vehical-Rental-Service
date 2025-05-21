package com.example.carrental.repository;

import com.example.carrental.model.Review;
import java.util.List;

public interface ReviewRepository {
    Review save(Review review);
    List<Review> findByVehicleId(String vehicleId);
    List<Review> findAll();
    void deleteById(Long id);
    Review findById(Long id);
}
