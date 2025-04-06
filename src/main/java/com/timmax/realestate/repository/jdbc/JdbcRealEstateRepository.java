package com.timmax.realestate.repository.jdbc;

import org.springframework.stereotype.Repository;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.RealEstateRepository;

import java.util.List;

@Repository
public class JdbcRealEstateRepository implements RealEstateRepository {

    @Override
    public RealEstate save(RealEstate meal, int userId) {
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
