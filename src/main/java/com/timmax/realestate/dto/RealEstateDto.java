package com.timmax.realestate.dto;

import java.beans.ConstructorProperties;
import java.util.Objects;

public class RealEstateDto {
    private final Integer id;
    private final String address;
    private final float square;

    @ConstructorProperties({"id", "address", "square"})
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
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        RealEstateDto that = (RealEstateDto) o;
        return Float.compare(square, that.square) == 0 &&
                Objects.equals(id, that.id) &&
                Objects.equals(address, that.address);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, address, square);
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
