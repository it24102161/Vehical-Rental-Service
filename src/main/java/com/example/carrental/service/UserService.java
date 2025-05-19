package com.example.carrental.service;

import com.example.carrental.model.AdminUser;
import com.example.carrental.model.CustomerUser;
import com.example.carrental.model.User;
import com.example.carrental.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User registerUser(User user) {
        // Check if username exists
        Optional<User> existingUser = userRepository.findUserByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new RuntimeException("Username already exists");
        }
        return userRepository.saveUser(user);
    }

    public User registerCustomer(CustomerUser customer) {
        return registerUser(customer);
    }

    public User registerAdmin(AdminUser admin) {
        return registerUser(admin);
    }

    public List<User> getAllUsers() {
        return userRepository.findAllUsers();
    }

    public Optional<User> getUserById(String userId) {
        return userRepository.findUserById(userId);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    public User updateUser(User user) {
        // Check if user exists
        Optional<User> existingUser = userRepository.findUserById(user.getUserId());
        if (!existingUser.isPresent()) {
            throw new RuntimeException("User not found");
        }
        return userRepository.updateUser(user);
    }

    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

    public boolean authenticateUser(String username, String password) {
        Optional<User> userOpt = userRepository.findUserByUsername(username);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            return user.getPassword().equals(password);
        }
        return false;
    }
}