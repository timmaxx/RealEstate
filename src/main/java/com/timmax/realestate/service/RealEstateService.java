package com.timmax.realestate.service;

import org.springframework.lang.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.RealEstateRepository;

import java.util.List;

import static com.timmax.realestate.util.Util.getValueIfIsNotNullOrGetFloatMaxValue;
import static com.timmax.realestate.util.Util.getValueIfIsNotNullOrGetFloatMinValue;
import static com.timmax.realestate.util.ValidationUtil.checkNotFound;

@Service
public class RealEstateService {
    private final RealEstateRepository repository;

    public RealEstateService(RealEstateRepository repository) {
        this.repository = repository;
    }

    public RealEstate get(int id, int userId) {
        return checkNotFound(repository.get(id, userId), id);
    }

    public void delete(int id, int userId) {
        checkNotFound(repository.delete(id, userId), id);
    }

    public List<RealEstate> getBetweenInclusive(
            @Nullable Float starSquare,
            @Nullable Float endSquare,
            int userId) {
        return repository.getBetweenHalfOpen(
                getValueIfIsNotNullOrGetFloatMinValue(starSquare),
                getValueIfIsNotNullOrGetFloatMaxValue(endSquare),
                userId);
    }

    public List<RealEstate> getAll(int userId) {
        return repository.getAll(userId);
    }

    public void update(RealEstate realEstate, int userId) {
        Assert.notNull(realEstate, "realEstate must not be null");
        checkNotFound(repository.save(realEstate, userId), realEstate.getId());
    }

    public RealEstate create(RealEstate realEstate, int userId) {
        Assert.notNull(realEstate, "realEstate must not be null");
        return repository.save(realEstate, userId);
    }
}
