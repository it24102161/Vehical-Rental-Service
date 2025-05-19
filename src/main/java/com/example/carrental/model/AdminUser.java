package com.example.carrental.model;

public class AdminUser extends User {
    private String role;
    private String adminId;

    public AdminUser() {
        super();
    }

    public AdminUser(String userId, String username, String password, String email,
                     String phoneNumber, String role, String adminId) {
        super(userId, username, password, email, phoneNumber);
        this.role = role;
        this.adminId = adminId;
    }

    // Getters and Setters
    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }

    @Override
    public String toString() {
        return super.toString() + "," + role + "," + adminId;
    }
}