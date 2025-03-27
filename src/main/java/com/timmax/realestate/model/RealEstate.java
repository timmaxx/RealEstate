package com.timmax.realestate.model;

public class RealEstate {
    private final String address;
    private final float square;

    public RealEstate(String address, float square) {
        this.address = address;
        this.square = square;
    }

    public String getAddress() {
        return address;
    }

    public float getSquare() {
        return square;
    }
}
