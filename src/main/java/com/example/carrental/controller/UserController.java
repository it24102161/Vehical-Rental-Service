package com.example.carrental.controller;

import com.example.carrental.model.AdminUser;
import com.example.carrental.model.CustomerUser;
import com.example.carrental.model.User;
import com.example.carrental.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegistrationForm(Model model) {
        model.addAttribute("user", new CustomerUser());
        return "users/register";
    }

    @PostMapping("/register")
    public String registerCustomer(@ModelAttribute CustomerUser customer, RedirectAttributes redirectAttributes) {
        try {
            userService.registerCustomer(customer);
            redirectAttributes.addFlashAttribute("message", "Registration successful. Please login.");
            return "redirect:/users/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/users/register";
        }
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "users/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password,
                        HttpSession session, RedirectAttributes redirectAttributes) {
        if (userService.authenticateUser(username, password)) {
            Optional<User> userOpt = userService.getUserByUsername(username);
            if (userOpt.isPresent()) {
                User user = userOpt.get();
                session.setAttribute("loggedInUser", user);

                // Check if user is admin
                if (user instanceof AdminUser) {
                    return "redirect:/admin/dashboard";
                } else {
                    return "redirect:/dashboard";
                }
            }
        }

        redirectAttributes.addFlashAttribute("error", "Invalid username or password");
        return "redirect:/users/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/users/login";
    }

    @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<User> userOpt = userService.getUserById(loggedInUser.getUserId());
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "users/profile";
        } else {
            return "redirect:/users/login";
        }
    }

    @GetMapping("/edit")
    public String showEditForm(HttpSession session, Model model) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        Optional<User> userOpt = userService.getUserById(loggedInUser.getUserId());
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "users/edit";
        } else {
            return "redirect:/users/login";
        }
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute User user, HttpSession session, RedirectAttributes redirectAttributes) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null) {
            return "redirect:/users/login";
        }

        user.setUserId(loggedInUser.getUserId());
        try {
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "Profile updated successfully");
            // Update session
            session.setAttribute("loggedInUser", user);
            return "redirect:/users/profile";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/users/edit";
        }
    }

    // Admin methods for user management
    @GetMapping("/admin/list")
    public String listAllUsers(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        List<User> users = userService.getAllUsers();
        model.addAttribute("users", users);
        return "admin/users/list";
    }

    @GetMapping("/admin/edit/{userId}")
    public String showAdminEditForm(@PathVariable String userId, Model model,
                                    HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        Optional<User> userOpt = userService.getUserById(userId);
        if (userOpt.isPresent()) {
            model.addAttribute("user", userOpt.get());
            return "admin/users/edit";
        } else {
            return "redirect:/users/admin/list";
        }
    }

    @PostMapping("/admin/edit/{userId}")
    public String adminUpdateUser(@PathVariable String userId, @ModelAttribute User user,
                                  RedirectAttributes redirectAttributes, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        user.setUserId(userId);
        try {
            userService.updateUser(user);
            redirectAttributes.addFlashAttribute("message", "User updated successfully");
            return "redirect:/users/admin/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/users/admin/edit/" + userId;
        }
    }

    @GetMapping("/admin/delete/{userId}")
    public String deleteUser(@PathVariable String userId, RedirectAttributes redirectAttributes,
                             HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        try {
            userService.deleteUser(userId);
            redirectAttributes.addFlashAttribute("message", "User deleted successfully");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
        }

        return "redirect:/users/admin/list";
    }

    @GetMapping("/admin/register")
    public String showAdminRegistrationForm(Model model, HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        model.addAttribute("admin", new AdminUser());
        return "admin/users/register";
    }

    @PostMapping("/admin/register")
    public String registerAdmin(@ModelAttribute AdminUser admin, RedirectAttributes redirectAttributes,
                                HttpSession session) {
        User loggedInUser = (User) session.getAttribute("loggedInUser");
        if (loggedInUser == null || !(loggedInUser instanceof AdminUser)) {
            return "redirect:/users/login";
        }

        try {
            admin.setRole("admin");
            userService.registerAdmin(admin);
            redirectAttributes.addFlashAttribute("message", "Admin registered successfully");
            return "redirect:/users/admin/list";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/users/admin/register";
        }
    }
    @GetMapping("/setup-admin")
    public String setupAdmin(RedirectAttributes redirectAttributes) {
        try {
            AdminUser admin = new AdminUser();
            admin.setUsername("admin");
            admin.setPassword("admin123");
            admin.setEmail("admin@example.com");
            admin.setPhoneNumber("1234567890");
            admin.setRole("admin");
            admin.setAdminId("ADMIN001");
            userService.registerAdmin(admin);
            redirectAttributes.addFlashAttribute("message", "Admin user created. Username: admin, Password: admin123");
            return "redirect:/users/login";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/users/login";
        }
    }
}
