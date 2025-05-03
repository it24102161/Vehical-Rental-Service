package com.rental.vehiclerental.service;

import com.rental.vehiclerental.model.User;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;

@Service
public class UserService {
    private static final String FILE_PATH = "users.txt";

    public void registerUser(User user) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getLicenseNumber());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 4) {
                    User user = new User(parts[0], parts[1], parts[2], parts[3]);
                    users.add(user);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return users;
    }

    public void updateUser(User updatedUser) {
        List<User> users = getAllUsers();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (user.getId().equals(updatedUser.getId())) {
                    bw.write(updatedUser.getId() + "," + updatedUser.getName() + "," + updatedUser.getEmail() + "," + updatedUser.getLicenseNumber());
                } else {
                    bw.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getLicenseNumber());
                }
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deleteUser(String id) {
        List<User> users = getAllUsers();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (User user : users) {
                if (!user.getId().equals(id)) {
                    bw.write(user.getId() + "," + user.getName() + "," + user.getEmail() + "," + user.getLicenseNumber());
                    bw.newLine();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public User getUserById(String id) {
        return getAllUsers().stream().filter(u -> u.getId().equals(id)).findFirst().orElse(null);
    }
}
