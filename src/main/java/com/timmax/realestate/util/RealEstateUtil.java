package com.timmax.realestate.util;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.dto.RealEstateDto;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RealEstateUtil {
    public static List<RealEstateDto> getDtos(Collection<RealEstate> realEstates) {
        return filterByPredicate(realEstates, realEstate -> true);
    }
/*
    //  В TopJava фильтрация по датам использовалась в сервисе-репозитории,
    //  а фильтрация по времени в этом классе.
    //  Т.е. сначала контроллер запрашивал у сервиса, который запрашивал у репозитория перечень,
    //  ограниченный только датами (но не временем), репозиторий отдавал сервису, сервис отдавал репозиторию.
    //  А потом контроллер сам фильтровал по времени.
    //  Считаю, что это не правильно.
    //  Но в TopJava был специфический случай фильтрации по времени и датам.
    //  Поэтому здесь таким-же путём не пойду.
    //  А раз так, то и в метод getDtos можно было-бы перенести функционал и filterByPredicate.
    public static List<RealEstateDto> getFilteredDtos(
            Collection<RealEstate> realEstates,
            Float startSquare,
            Float endSquare) {
        return filterByPredicate(
                realEstates,
                realEstate -> Util.isBetweenHalfOpen(
                        realEstate.getSquare(),
                        startSquare,
                        endSquare
                )
        );
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
