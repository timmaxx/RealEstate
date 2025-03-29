package com.timmax.realestate.repository;

import com.timmax.realestate.model.RealEstate;

import java.util.Collection;

// TODO add userId
public interface RealEstateRepository {
    // null if updated realEstate does not belong to userId
    RealEstate save(RealEstate realEstate);

    // false if realEstate does not belong to userId
    boolean delete(int id);

    // null if realEstate does not belong to userId
    RealEstate get(int id);

    // ORDERED address asc
    Collection<RealEstate> getAll();
}
