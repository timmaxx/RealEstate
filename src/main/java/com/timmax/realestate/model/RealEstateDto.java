package com.timmax.realestate.model;

import java.time.LocalDateTime;

public class RealEstateDto {
    private final LocalDateTime dateTime;
    private final String address;
    private final int square;

    private final boolean sameAddress;

    public RealEstateDto(LocalDateTime dateTime, String address, int square, boolean sameAddress) {
        this.dateTime = dateTime;
        this.address = address;
        this.square = square;
        this.sameAddress = sameAddress;
    }

    @Override
    public String toString() {
        return "RealEstateDto{" +
                "dateTime=" + dateTime +
                ", address='" + address + '\'' +
                ", square=" + square +
                ", sameAddress=" + sameAddress +
                '}';
    }
}
