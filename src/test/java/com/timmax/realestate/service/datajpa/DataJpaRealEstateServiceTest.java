package com.timmax.realestate.service.datajpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;
import com.timmax.realestate.RealEstateTestData;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.service.AbstractRealEstateServiceTest;
import com.timmax.realestate.util.exception.NotFoundException;

import static com.timmax.realestate.RealEstateTestData.*;
import static com.timmax.realestate.Profiles.DATAJPA;
import static com.timmax.realestate.UserTestData.*;

@ActiveProfiles(DATAJPA)
class DataJpaRealEstateServiceTest extends AbstractRealEstateServiceTest {
    @Test
    void getWithUser() {
        RealEstate adminRealEstate = service.getWithUser(ADMIN_REAL_ESTATE_ID, ADMIN_ID);
        REAL_ESTATE_MATCHER.assertMatch(adminRealEstate, adminRealEstate1);
        USER_MATCHER.assertMatch(adminRealEstate.getUser(), admin);
    }

    @Test
    void getWithUserNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithUser(RealEstateTestData.NOT_FOUND, ADMIN_ID));
    }
}
