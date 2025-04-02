package com.timmax.realestate.repository.inmemory;

import org.springframework.stereotype.Repository;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.RealEstateRepository;
import com.timmax.realestate.util.RealEstateUtil;
import com.timmax.realestate.util.Util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.timmax.realestate.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static com.timmax.realestate.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository
public class InMemoryRealEstateRepository implements RealEstateRepository {

    // Map  userId -> realEstateRepository
    private final Map<Integer, InMemoryBaseRepository<RealEstate>> userRealEstatesMap = new ConcurrentHashMap<>();

    {
        RealEstateUtil.realEstates.forEach(realEstate -> save(realEstate, USER_ID));
        save(new RealEstate("Address8", 80), ADMIN_ID);
        save(new RealEstate("Address9", 90), ADMIN_ID);
    }

    @Override
    public RealEstate save(RealEstate realEstate, int userId) {
        InMemoryBaseRepository<RealEstate> realEstates = userRealEstatesMap.computeIfAbsent(userId, uId -> new InMemoryBaseRepository<>());
        return realEstates.save(realEstate);
    }

    @Override
    public boolean delete(int id, int userId) {
        InMemoryBaseRepository<RealEstate> realEstates = userRealEstatesMap.get(userId);
        return realEstates != null && realEstates.delete(id);
    }

    @Override
    public RealEstate get(int id, int userId) {
        InMemoryBaseRepository<RealEstate> realEstates = userRealEstatesMap.get(userId);
        return realEstates == null ? null : realEstates.get(id);
    }

    @Override
    public List<RealEstate> getBetweenHalfOpen(
            Float startSquare,
            Float endSquare,
            int userId) {
        return filterByPredicate(
                userId,
                realEstate -> Util.isBetweenHalfOpen(
                        realEstate.getSquare(),
                        startSquare,
                        endSquare
                )
        );
    }

    @Override
    public List<RealEstate> getAll(int userId) {
        return filterByPredicate(userId, realEstate -> true);
    }

    private List<RealEstate> filterByPredicate(int userId, Predicate<RealEstate> filter) {
        InMemoryBaseRepository<RealEstate> realEstates = userRealEstatesMap.get(userId);
        return realEstates == null ? Collections.emptyList() :
                realEstates.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(RealEstate::getAddress))
                        .collect(Collectors.toList());
    }
}
