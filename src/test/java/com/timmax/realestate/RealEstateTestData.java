package com.timmax.realestate;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.dto.RealEstateDto;

import java.util.List;

import static com.timmax.realestate.model.AbstractBaseEntity.START_SEQ;

public class RealEstateTestData {
    public static final MatcherFactory.Matcher<RealEstate> REAL_ESTATE_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(RealEstate.class, "user");
    public static MatcherFactory.Matcher<RealEstateDto> DTO_MATCHER = MatcherFactory.usingEqualsComparator(RealEstateDto.class);

    public static final int NOT_FOUND = 10;
    public static final int REAL_ESTATE1_ID = START_SEQ + 3;
    public static final int ADMIN_REAL_ESTATE_ID = START_SEQ + 10;

    public static final RealEstate realEstate1 = new RealEstate(REAL_ESTATE1_ID, "Address1", 10);
    public static final RealEstate realEstate2 = new RealEstate(REAL_ESTATE1_ID + 1, "Address2", 20);
    public static final RealEstate realEstate3 = new RealEstate(REAL_ESTATE1_ID + 2, "Address3", 30);
    public static final RealEstate realEstate4 = new RealEstate(REAL_ESTATE1_ID + 3, "Address4", 40);
    public static final RealEstate realEstate5 = new RealEstate(REAL_ESTATE1_ID + 4, "Address5", 50);
    public static final RealEstate realEstate6 = new RealEstate(REAL_ESTATE1_ID + 5, "Address6", 60);
    public static final RealEstate realEstate7 = new RealEstate(REAL_ESTATE1_ID + 6, "Address7", 70);
    public static final RealEstate adminRealEstate1 = new RealEstate(ADMIN_REAL_ESTATE_ID, "Address10", 110);
    public static final RealEstate adminRealEstate2 = new RealEstate(ADMIN_REAL_ESTATE_ID + 1, "Address11", 111);

    public static final List<RealEstate> realEstates =
            List.of(
                    realEstate1, realEstate2, realEstate3, realEstate4, realEstate5, realEstate6, realEstate7
            );

    public static final List<RealEstate> adminRealEstates =
            List.of(
                    adminRealEstate1, adminRealEstate2
            );

    public static RealEstate getNew() {
        //  ToDo: См. web.xml вблизи с <filter-name>encodingFilter</filter-name>
        // return new RealEstate(null, "Address", 1);
        return new RealEstate(null, "Адрес", 1);
    }

    public static RealEstate getUpdated() {
        return new RealEstate(REAL_ESTATE1_ID, "Обновлённый Address", 111);
    }
}
