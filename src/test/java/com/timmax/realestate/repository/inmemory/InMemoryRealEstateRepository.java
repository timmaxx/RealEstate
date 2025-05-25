package com.timmax.realestate.repository.inmemory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import com.timmax.realestate.RealEstateTestData;
import com.timmax.realestate.UserTestData;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.repository.RealEstateRepository;
import com.timmax.realestate.util.Util;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Predicate;

@Repository
public class InMemoryRealEstateRepository implements RealEstateRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryRealEstateRepository.class);

    // Map  userId -> realEstateRepository
    private final Map<Integer, InMemoryBaseRepository<RealEstate>> usersRealEstatesMap = new ConcurrentHashMap<>();

    //  ToDo:   Эту инициализацию нужно убрать из реализации репозитория.
    //          Ведь инициализация СУБД-репозитория делается через populateDB.sql
    //          и в реализации самих репозиториев нет инициализации.
    {
        var userRealEstates = new InMemoryBaseRepository<RealEstate>();
        RealEstateTestData.realEstates.forEach(userRealEstates::put);
        usersRealEstatesMap.put(UserTestData.USER_ID, userRealEstates);

        var adminRealEstates = new InMemoryBaseRepository<RealEstate>();
        RealEstateTestData.realEstates.forEach(adminRealEstates::put);
        usersRealEstatesMap.put(UserTestData.ADMIN_ID, adminRealEstates);
    }

    @Override
    public RealEstate save(RealEstate realEstate, int userId) {
        Objects.requireNonNull(realEstate, "realEstate must not be null");
        var realEstates = usersRealEstatesMap.computeIfAbsent(userId, uId -> new InMemoryBaseRepository<>());
        return realEstates.save(realEstate);
    }

    @PostConstruct
    public void postConstruct() {
        log.info("+++ PostConstruct");
    }

    @PreDestroy
    public void preDestroy() {
        log.info("+++ PreDestroy");
    }

    @Override
    public boolean delete(int id, int userId) {
        var realEstates = usersRealEstatesMap.get(userId);
        return realEstates != null && realEstates.delete(id);
    }

    @Override
    public RealEstate get(int id, int userId) {
        var realEstates = usersRealEstatesMap.get(userId);
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
        var realEstates = usersRealEstatesMap.get(userId);
        return realEstates == null ? Collections.emptyList() :
                realEstates.getCollection().stream()
                        .filter(filter)
                        .sorted(Comparator.comparing(RealEstate::getAddress))
                        .toList();
    }
}
