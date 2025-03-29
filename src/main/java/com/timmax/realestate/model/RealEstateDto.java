package com.timmax.realestate.model;

public class RealEstateDto {
    private final Integer id;
    private final String address;
    private final float square;

    public RealEstateDto(Integer id, String address, float square) {
        this.id = id;
        this.address = address;
        this.square = square;
    }

    public Integer getId() {
        return id;
    }

    public String getAddress() {
        return address;
    }

    public float getSquare() {
        return square;
    }

    @Override
    public String toString() {
        return "RealEstateDto{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", square=" + square +
                '}';
    }
}
