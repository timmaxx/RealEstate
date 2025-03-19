package com.timmax.realestate.util;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.model.RealEstateWithSameAddress;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class RealEstateUtil {
    public static void main(String[] args) {
        List<RealEstate> realEstates = Arrays.asList(
                new RealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address1", 50),
                new RealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address1", 50),
                new RealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address1", 50),
                new RealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address2", 50),
                new RealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address3", 50)
        );

        List<RealEstateWithSameAddress> realEstateWithSameAddressesDto = filterByCycles(realEstates);
        realEstateWithSameAddressesDto.forEach(System.out::println);

        System.out.println();

        List<RealEstateWithSameAddress> realEstateWithSameAddressesDto2 = filterByStreams(realEstates);
        realEstateWithSameAddressesDto2.forEach(System.out::println);
    }

    public static List<RealEstateWithSameAddress> filterByCycles(List<RealEstate> realEstates) {
        // Done: return filtered list with sameAddress. Implement by cycles
        final Map<String, Integer> address_count_Map = new HashMap<>();
        realEstates.forEach(
                realEstate -> address_count_Map.merge(
                        realEstate.getAddress(),
                        1,
                        Integer::sum
                )
        );

        List<RealEstateWithSameAddress> result = new ArrayList<>();
        for (RealEstate realEstate : realEstates) {
            result.add(
                    createTo(
                            realEstate,
                            address_count_Map.get(realEstate.getAddress()) > 1
                    )
            );
        }
        return result;
    }

    public static List<RealEstateWithSameAddress> filterByStreams(List<RealEstate> realEstates) {
        // Done: Implement by streams
        final Map<String, Long> address_count_Map = realEstates.stream()
                .collect(
                        Collectors.groupingBy(RealEstate::getAddress, Collectors.counting())
                );

        return realEstates.stream()
                .map(realEstate -> createTo(realEstate, address_count_Map.get(realEstate.getAddress()).intValue() > 1))
                .collect(Collectors.toList())
                ;
    }

    private static RealEstateWithSameAddress createTo(
            RealEstate realEstate,
            boolean sameAddress
    ) {
        return new RealEstateWithSameAddress(
                realEstate.getDateTime(),
                realEstate.getAddress(),
                realEstate.getSquare(),
                sameAddress);
    }
}
