package com.example.carrental.controller;

import com.example.carrental.model.Review;
import com.example.carrental.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class ReviewController {

    @Autowired
    private ReviewService service;

    @GetMapping("/submit-review")
    public String showForm(Model model) {
        model.addAttribute("review", new Review());
        return "submit_review";
    }

    @PostMapping("/submit-review")
    public String submitReview(@ModelAttribute Review review) {
        service.saveReview(review);
        return "redirect:/view-reviews?vehicleId=" + review.getVehicleId();
    }

    @GetMapping("/view-reviews")
    public String viewReviews(@RequestParam String vehicleId, Model model) {
        model.addAttribute("reviews", service.getReviewsByVehicleId(vehicleId));
        return "view_reviews";
    }

    @GetMapping("/admin")
    public String adminPanel(Model model) {
        model.addAttribute("reviews", service.getAllReviews());
        return "admin_panel";
    }

    @GetMapping("/delete-review")
    public String deleteReview(@RequestParam Long id) {
        service.deleteReview(id);
        return "redirect:/admin";
    }
}
