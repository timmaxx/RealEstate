package com.timmax.realestate.service.datajpa;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import com.timmax.realestate.RealEstateTestData;
import com.timmax.realestate.UserTestData;
import com.timmax.realestate.model.User;
import com.timmax.realestate.service.AbstractUserServiceTest;
import com.timmax.realestate.util.exception.NotFoundException;

import static com.timmax.realestate.RealEstateTestData.REAL_ESTATE_MATCHER;
import static com.timmax.realestate.Profiles.DATAJPA;
import static com.timmax.realestate.UserTestData.*;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    public void getWithMeals() {
        User user = service.getWithRealEstates(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
        REAL_ESTATE_MATCHER.assertMatch(user.getRealEstates(), RealEstateTestData.realEstates);
    }

    @Test
    public void getWithMealsNotFound() {
        Assert.assertThrows(NotFoundException.class,
                () -> service.getWithRealEstates(NOT_FOUND));
    }
}
