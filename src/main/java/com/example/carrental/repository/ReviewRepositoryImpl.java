package com.example.carrental.repository;

import com.example.carrental.model.Review;
import org.springframework.stereotype.Repository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class ReviewRepositoryImpl implements ReviewRepository {
    private final Map<Long, Review> reviews = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    @Override
    public Review save(Review review) {
        if (review.getId() == null) {
            review.setId(idGenerator.getAndIncrement());
        }
        reviews.put(review.getId(), review);
        return review;
    }

    @Override
    public List<Review> findByVehicleId(String vehicleId) {
        return reviews.values().stream()
                .filter(review -> review.getVehicleId().equals(vehicleId))
                .toList();
    }

    @Override
    public List<Review> findAll() {
        return new ArrayList<>(reviews.values());
    }

    @Override
    public void deleteById(Long id) {
        reviews.remove(id);
    }

    @Override
    public Review findById(Long id) {
        return reviews.get(id);
    }
} 