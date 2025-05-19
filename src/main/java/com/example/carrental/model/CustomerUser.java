package com.example.carrental.model;

public class CustomerUser extends User {
    private String licenseNumber;
    private String licenseExpiry;
    private String membershipType; // regular, premium

    public CustomerUser() {
        super();
    }

    public CustomerUser(String userId, String username, String password, String email,
                        String phoneNumber, String licenseNumber, String licenseExpiry,
                        String membershipType) {
        super(userId, username, password, email, phoneNumber);
        this.licenseNumber = licenseNumber;
        this.licenseExpiry = licenseExpiry;
        this.membershipType = membershipType;
    }

    // Getters and Setters
    public String getLicenseNumber() {
        return licenseNumber;
    }

    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseExpiry() {
        return licenseExpiry;
    }

    public void setLicenseExpiry(String licenseExpiry) {
        this.licenseExpiry = licenseExpiry;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    @Override
    public String toString() {
        return super.toString() + "," + licenseNumber + "," + licenseExpiry + "," + membershipType;
    }
}
