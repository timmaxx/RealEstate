package com.timmax.realestate.model;

public class RealEstateDto {
    private final String address;
    private final float square;

    public RealEstateDto(String address, float square) {
        this.address = address;
        this.square = square;
    }

    @Override
    public String toString() {
        return "RealEstateDto{" +
                ", address='" + address + '\'' +
                ", square=" + square +
                '}';
    }
}
