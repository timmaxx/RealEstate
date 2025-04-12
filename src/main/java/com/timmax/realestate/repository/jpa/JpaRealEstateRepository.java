package com.timmax.realestate.repository.jpa;

import org.springframework.stereotype.Repository;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.RealEstateRepository;

import java.util.List;

@Repository
public class JpaRealEstateRepository implements RealEstateRepository {

    @Override
    public RealEstate save(RealEstate realEstate, int userId) {
        return null;
    }

    @Override
    public boolean delete(int id, int userId) {
        return false;
    }

    @Override
    public RealEstate get(int id, int userId) {
        return null;
    }

    @Override
    public List<RealEstate> getAll(int userId) {
        return null;
    }

    @Override
    public List<RealEstate> getBetweenHalfOpen(Float startSquare, Float endSquare, int userId) {
        return null;
    }
}
