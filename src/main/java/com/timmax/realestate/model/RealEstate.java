package com.timmax.realestate.model;

public class RealEstate extends AbstractBaseEntity {
    private final String address;
    private final float square;

    public RealEstate(String address, float square) {
        this(null, address, square);
    }

    public RealEstate(Integer id, String address, float square) {
        super(id);
        this.address = address;
        this.square = square;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public float getSquare() {
        return square;
    }

    @Override
    public String toString() {
        return "RealEstate{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", square=" + square +
                '}';
    }
}
