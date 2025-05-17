package com.example.carrental.reviewapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import java.time.LocalDateTime;

@SpringBootApplication
public class ReviewAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(ReviewAppApplication.class, args);
    }

    @Bean
    public CommandLineRunner initData(ReviewController reviewController) {
        return args -> {
            // Add some test reviews if the database is empty
            if (reviewController.getAllReviews().isEmpty()) {
                Review review1 = new Review("John Doe", "Porsche 911", 5, "Amazing car!");
                review1.setStatus("approved");
                reviewController.submitReview(review1.getName(), review1.getVehicle(),
                        review1.getRating(), review1.getComments());

                Review review2 = new Review("Jane Smith", "BMW M5", 4, "Great performance");
                review2.setStatus("pending");
                reviewController.submitReview(review2.getName(), review2.getVehicle(),
                        review2.getRating(), review2.getComments());
            }
        };
    }
}