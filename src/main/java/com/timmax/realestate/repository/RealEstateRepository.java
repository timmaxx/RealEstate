package com.timmax.realestate.repository;

import com.timmax.realestate.model.RealEstate;

import java.util.Collection;

public interface RealEstateRepository {
    // null if not found, when updated
    RealEstate save(RealEstate meal);

    // false if not found
    boolean delete(int id);

    // null if not found
    RealEstate get(int id);

    Collection<RealEstate> getAll();
}
