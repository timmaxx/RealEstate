package com.timmax.realestate.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class RealEstate {
    private final LocalDateTime dateTime;
    private final String address;
    private final int square;

    public RealEstate(LocalDateTime dateTime, String address, int square) {
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

    public LocalDate getDate() {
        return dateTime.toLocalDate();
    }

    public LocalTime getTime() {
        return dateTime.toLocalTime();
    }
}
