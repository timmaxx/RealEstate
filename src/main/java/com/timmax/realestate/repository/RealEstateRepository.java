package com.timmax.realestate.repository;

import com.timmax.realestate.model.RealEstate;

import java.util.List;

public interface RealEstateRepository {
    // null if updated realEstate does not belong to userId
    RealEstate save(RealEstate realEstate, int userId);

    // false if realEstate does not belong to userId
    boolean delete(int id, int userId);

    // null if realEstate does not belong to userId
    RealEstate get(int id, int userId);

    // ORDERED address asc
    List<RealEstate> getAll(int userId);
}
