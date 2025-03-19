package com.timmax.realestate.util;

import com.timmax.realestate.model.UserRealEstate;
import com.timmax.realestate.model.UserRealEstateWithSameAddress;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

public class UserRealEstateUtil {
    public static void main(String[] args) {
        List<UserRealEstate> userRealEstates = Arrays.asList(
                new UserRealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address1", 50),
                new UserRealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address1", 50),
                new UserRealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address1", 50),
                new UserRealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address2", 50),
                new UserRealEstate(LocalDateTime.of(2025, Month.JANUARY, 1, 0, 0), "Address3", 50)
        );

        List<UserRealEstateWithSameAddress> userRealEstateWithSameAddressesDto = filterByCycles(userRealEstates);
        userRealEstateWithSameAddressesDto.forEach(System.out::println);

        System.out.println();

        List<UserRealEstateWithSameAddress> userRealEstateWithSameAddressesDto2 = filterByStreams(userRealEstates);
        userRealEstateWithSameAddressesDto2.forEach(System.out::println);
    }

    public static List<UserRealEstateWithSameAddress> filterByCycles(List<UserRealEstate> userRealEstates) {
        // Done: return filtered list with sameAddress. Implement by cycles
        final Map<String, Integer> address_count_Map = new HashMap<>();
        userRealEstates.forEach(
                userRealEstate -> address_count_Map.merge(
                        userRealEstate.getAddress(),
                        1,
                        Integer::sum
                )
        );

        List<UserRealEstateWithSameAddress> result = new ArrayList<>();
        for (UserRealEstate userRealEstate : userRealEstates) {
            result.add(
                    createTo(
                            userRealEstate,
                            address_count_Map.get(userRealEstate.getAddress()) > 1
                    )
            );
        }
        return result;
    }

    public static List<UserRealEstateWithSameAddress> filterByStreams(List<UserRealEstate> userRealEstates) {
        // Done: Implement by streams
        final Map<String, Long> address_count_Map = userRealEstates.stream()
                .collect(
                        Collectors.groupingBy(UserRealEstate::getAddress, Collectors.counting())
                );

        return userRealEstates.stream()
                .map(userRealEstate -> createTo(userRealEstate, address_count_Map.get(userRealEstate.getAddress()).intValue() > 1))
                .collect(Collectors.toList())
                ;
    }

    private static UserRealEstateWithSameAddress createTo(
            UserRealEstate userRealEstate,
            boolean sameAddress
    ) {
        return new UserRealEstateWithSameAddress(
                userRealEstate.getDateTime(),
                userRealEstate.getAddress(),
                userRealEstate.getSquare(),
                sameAddress);
    }
}
