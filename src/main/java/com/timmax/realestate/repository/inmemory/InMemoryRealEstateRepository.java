package com.timmax.realestate.repository.inmemory;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.RealEstateRepository;
import com.timmax.realestate.util.RealEstateUtil;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryRealEstateRepository implements RealEstateRepository {
    private final Map<Integer, RealEstate> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        RealEstateUtil.realEstates.forEach(this::save);
    }

    @Override
    public RealEstate save(RealEstate realEstate) {
        if (realEstate.isNew()) {
            realEstate.setId(counter.incrementAndGet());
            repository.put(realEstate.getId(), realEstate);
            return realEstate;
        }
        // handle case: update, but not present in storage
        return repository.computeIfPresent(realEstate.getId(), (id, oldRealEstate) -> realEstate);
    }

    @Override
    public boolean delete(int id) {
        return repository.remove(id) != null;
    }

    @Override
    public RealEstate get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<RealEstate> getAll() {
        return repository.values();
    }
}
