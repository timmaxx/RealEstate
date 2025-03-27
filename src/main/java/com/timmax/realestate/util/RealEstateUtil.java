package com.timmax.realestate.util;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.model.RealEstateDto;

import java.util.*;
import java.util.stream.Collectors;

public class RealEstateUtil {
    public static void main(String[] args) {
        List<RealEstate> realEstates = Arrays.asList(
                new RealEstate("Address1", 50),
                new RealEstate("Address2", 50),
                new RealEstate("Address3", 50),
                new RealEstate("Address4", 50),
                new RealEstate("Address5", 50)
        );

        List<RealEstateDto> realEstateWithSameAddressesDto = filterByCycles(realEstates);
        realEstateWithSameAddressesDto.forEach(System.out::println);

        System.out.println();

        List<RealEstateDto> realEstateWithSameAddressesDto2 = filterByStreams(realEstates);
        realEstateWithSameAddressesDto2.forEach(System.out::println);
    }

    public static List<RealEstateDto> filterByCycles(List<RealEstate> realEstates) {
        //  Done:   Implement by cycles
        List<RealEstateDto> result = new ArrayList<>();
        for (RealEstate realEstate : realEstates) {
            result.add(createDto(realEstate));
        }
        return result;
    }

    public static List<RealEstateDto> filterByStreams(List<RealEstate> realEstates) {
        //  Done:   Implement by streams
        return realEstates.stream()
                // .map(realEstate -> createDto(realEstate))
                .map(RealEstateUtil::createDto)
                .collect(Collectors.toList())
                ;
    }

    private static RealEstateDto createDto(RealEstate realEstate) {
        return new RealEstateDto(realEstate.getAddress(), realEstate.getSquare());
    }
}
