package com.timmax.realestate.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import com.timmax.realestate.RealEstateTestData;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.service.AbstractRealEstateServiceTest;
import com.timmax.realestate.util.exception.NotFoundException;

import static com.timmax.realestate.RealEstateTestData.*;
import static com.timmax.realestate.Profiles.DATAJPA;
import static com.timmax.realestate.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaRealEstateServiceTest extends AbstractRealEstateServiceTest {
    @Test
    public void getWithUser() {
        RealEstate adminMeal = service.getWithUser(ADMIN_REAL_ESTATE_ID, ADMIN_ID);
        REAL_ESTATE_MATCHER.assertMatch(adminMeal, adminRealEstate1);
        USER_MATCHER.assertMatch(adminMeal.getUser(), admin);
    }

    @Test
    public void getWithUserNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithUser(RealEstateTestData.NOT_FOUND, ADMIN_ID));
    }
}
