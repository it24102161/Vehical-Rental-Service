package com.example.carrental.repository;

import com.example.carrental.model.AdminUser;
import com.example.carrental.model.CustomerUser;
import com.example.carrental.model.User;
import com.example.carrental.util.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Repository;

@Repository
public class UserRepository {
    private static final String USERS_FILE = "src/main/resources/data/users.txt";

    public UserRepository() {
        // Ensure the file exists
        FileUtils.createFileIfNotExists(USERS_FILE);
    }

    public User saveUser(User user) {
        if (user.getUserId() == null || user.getUserId().isEmpty()) {
            user.setUserId(UUID.randomUUID().toString());
        }

        FileUtils.writeToFile(USERS_FILE, user.toString(), true);
        return user;
    }

    public List<User> findAllUsers() {
        List<User> users = new ArrayList<>();
        List<String> lines = FileUtils.readFromFile(USERS_FILE);

        for (String line : lines) {
            String[] parts = line.split(",");
            if (parts.length >= 5) { // Basic user fields
                if (parts.length >= 8 && !parts[5].equals("admin")) { // CustomerUser
                    CustomerUser customer = new CustomerUser(
                            parts[0], parts[1], parts[2], parts[3], parts[4],
                            parts[5], parts[6], parts[7]
                    );
                    users.add(customer);
                } else if (parts.length >= 7 && parts[5].equals("admin")) { // AdminUser
                    AdminUser admin = new AdminUser(
                            parts[0], parts[1], parts[2], parts[3], parts[4],
                            parts[5], parts[6]
                    );
                    users.add(admin);
                } else { // Basic User
                    User basicUser = new User(
                            parts[0], parts[1], parts[2], parts[3], parts[4]
                    );
                    users.add(basicUser);
                }
            }
        }

        return users;
    }

    public Optional<User> findUserById(String userId) {
        return findAllUsers().stream()
                .filter(user -> user.getUserId().equals(userId))
                .findFirst();
    }

    public Optional<User> findUserByUsername(String username) {
        return findAllUsers().stream()
                .filter(user -> user.getUsername().equals(username))
                .findFirst();
    }

    public void deleteUser(String userId) {
        Optional<User> userOpt = findUserById(userId);
        if (userOpt.isPresent()) {
            User user = userOpt.get();
            FileUtils.removeFromFile(USERS_FILE, userId);
        }
    }

    public User updateUser(User user) {
        Optional<User> existingUserOpt = findUserById(user.getUserId());
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            FileUtils.updateInFile(USERS_FILE, existingUser.getUserId(), user.toString());
        }
        return user;
    }
}