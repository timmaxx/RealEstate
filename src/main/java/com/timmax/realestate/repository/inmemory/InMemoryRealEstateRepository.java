package com.timmax.realestate.repository.inmemory;

import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.RealEstateRepository;
import com.timmax.realestate.util.RealEstateUtil;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.timmax.realestate.repository.inmemory.InMemoryUserRepository.ADMIN_ID;
import static com.timmax.realestate.repository.inmemory.InMemoryUserRepository.USER_ID;

@Repository
public class InMemoryRealEstateRepository implements RealEstateRepository {

    // Map  userId -> (realEstateId -> realEstate)
    private final Map<Integer, Map<Integer, RealEstate>> userRealEstatesMap = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        RealEstateUtil.realEstates.forEach(realEstate -> save(realEstate, USER_ID));
        save(new RealEstate("Address8", 80), ADMIN_ID);
        save(new RealEstate("Address9", 90), ADMIN_ID);
    }

    @Override
    public RealEstate save(RealEstate realEstate, int userId) {
        // We cannot use method reference "ConcurrentHashMap::new" here. It will be equivalent wrong "new ConcurrentHashMap<>(userId)"
        Map<Integer, RealEstate> realEstates = userRealEstatesMap.computeIfAbsent(userId, uId -> new ConcurrentHashMap<>());
        if (realEstate.isNew()) {
            realEstate.setId(counter.incrementAndGet());
            realEstates.put(realEstate.getId(), realEstate);
            return realEstate;
        }
        return realEstates.computeIfPresent(realEstate.getId(), (id, oldRealEstate) -> realEstate);
    }

    @Override
    public boolean delete(int id, int userId) {
        Map<Integer, RealEstate> realEstates = userRealEstatesMap.get(userId);
        return realEstates != null && realEstates.remove(id) != null;
    }

    @Override
    public RealEstate get(int id, int userId) {
        Map<Integer, RealEstate> realEstates = userRealEstatesMap.get(userId);
        return realEstates == null ? null : realEstates.get(id);
    }

    @Override
    public List<RealEstate> getAll(int userId) {
        return filterByPredicate(userId, realEstate -> true);
    }

    private List<RealEstate> filterByPredicate(int userId, Predicate<RealEstate> filter) {
        Map<Integer, RealEstate> realEstates = userRealEstatesMap.get(userId);
        return CollectionUtils.isEmpty(realEstates) ? Collections.emptyList() :
                realEstates.values().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(RealEstate::getAddress))
                        .collect(Collectors.toList());
    }
}
