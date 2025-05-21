package com.example.carrental.model;

public class VerifiedReview extends Review {
    private String bookingId;

    public VerifiedReview() {
        super();
    }

    public VerifiedReview(Long id, String userId, String vehicleId, int rating, String comment, String bookingId) {
        super(id, userId, vehicleId, rating, comment);
        this.bookingId = bookingId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    @Override
    public String getType() {
        return "Verified";
    }
}
