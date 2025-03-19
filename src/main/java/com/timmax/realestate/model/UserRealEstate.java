package com.timmax.realestate.model;

import java.time.LocalDateTime;

public class UserRealEstate {
    private final LocalDateTime dateTime;
    private final String address;
    private final int square;

    public UserRealEstate(LocalDateTime dateTime, String address, int square) {
        this.dateTime = dateTime;
        this.address = address;
        this.square = square;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getAddress() {
        return address;
    }

    public int getSquare() {
        return square;
    }
}
