package com.example.carrental.reviewapp;

public class VerifiedReview extends Review {
    private String verificationMethod;

    public VerifiedReview(String name, String vehicle, int rating, String comments, String verificationMethod) {
        super(name, vehicle, rating, comments);
        this.verificationMethod = verificationMethod;
        setVerified(true);
    }

    public String getVerificationMethod() {
        return verificationMethod;
    }

    @Override
    public String toString() {
        return super.toString() + " [Verified via: " + verificationMethod + "]";
    }
}