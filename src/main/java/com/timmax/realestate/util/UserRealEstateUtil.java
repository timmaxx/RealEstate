package com.timmax.realestate.util;

import com.timmax.realestate.model.UserRealEstate;
import com.timmax.realestate.model.UserRealEstateWithSameAddress;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

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
    }

    public static List<UserRealEstateWithSameAddress> filterByCycles(List<UserRealEstate> userRealEstates) {
        // TODO return filtered list with sameAddress. Implement by cycles
        return null;
    }

    public static List<UserRealEstateWithSameAddress> filterByStreams(List<UserRealEstate> userRealEstates) {
        // TODO Implement by streams
        return null;
    }
}
