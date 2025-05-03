package com.rental.vehiclerental.model;

public class CustomerUser extends User {
    private String subscriptionType;

    public CustomerUser(String id, String name, String email, String licenseNumber, String subscriptionType) {
        super(id, name, email, licenseNumber);
        this.subscriptionType = subscriptionType;
    }

    public String getSubscriptionType() {
        return subscriptionType;
    }

    public void setSubscriptionType(String subscriptionType) {
        this.subscriptionType = subscriptionType;
    }
}

