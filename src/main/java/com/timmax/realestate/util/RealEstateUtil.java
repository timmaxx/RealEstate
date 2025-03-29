package com.timmax.realestate.util;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.model.RealEstateDto;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RealEstateUtil {
    public static final List<RealEstate> realEstates = Arrays.asList(
            new RealEstate("Address1", 10),
            new RealEstate("Address2", 20),
            new RealEstate("Address3", 30),
            new RealEstate("Address4", 40),
            new RealEstate("Address5", 50)
    );

    public static List<RealEstateDto> getDtos(Collection<RealEstate> realEstates) {
        return filterByPredicate(realEstates, realEstate -> true);
    }
/*
    public static List<RealEstateDto> getFilteredDtos(Collection<RealEstate> realEstates, LocalTime startTime, LocalTime endTime) {
        return filterByPredicate(realEstates, realEstate -> DateTimeUtil.isBetweenHalfOpen(realEstate.getTime(), startTime, endTime));
    }
*/
    private static List<RealEstateDto> filterByPredicate(Collection<RealEstate> realEstates, Predicate<RealEstate> filter) {
        return realEstates.stream()
                .filter(filter)
                //  .map(realEstate -> createDto(realEstate))
                .map(RealEstateUtil::createDto)
                .collect(Collectors.toList());
    }

    private static RealEstateDto createDto(RealEstate realEstate) {
        return new RealEstateDto(realEstate.getId(), realEstate.getAddress(), realEstate.getSquare());
    }
}
