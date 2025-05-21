// HomeController.java
package com.example.carrental.controller;

import com.example.carrental.model.AdminUser;
import com.example.carrental.model.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "home";
    }

    @GetMapping("/dashboard")
    public String dashboard(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        if (loggedInUser instanceof AdminUser) {
            return "redirect:/admin/dashboard";
        }

        return "dashboard";
    }

    @GetMapping("/admin/dashboard")
    public String adminDashboard(HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        return "admin/dashboard";
    }
}