package com.timmax.realestate.model;

public class RealEstate extends AbstractBaseEntity {
    private String address;
    private float square;

    public RealEstate() {
    }

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

    public void setAddress(String address) {
        this.address = address;
    }

    public void setSquare(float square) {
        this.square = square;
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
