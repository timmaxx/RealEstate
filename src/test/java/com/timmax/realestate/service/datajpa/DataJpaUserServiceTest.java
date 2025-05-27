package com.timmax.realestate.service.datajpa;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
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
class DataJpaUserServiceTest extends AbstractUserServiceTest {
    @Test
    void getWithAdminRealEstates() {
        User admin = service.getWithRealEstates(ADMIN_ID);
        USER_MATCHER.assertMatch(admin, UserTestData.admin);
        REAL_ESTATE_MATCHER.assertMatch(admin.getRealEstates(), RealEstateTestData.adminRealEstates);

/*
        //  ToDo:   Нужно привязать тестовые объекты RealEstate к соответствующим пользователям.
        //          Если раскомментировать, то тест завалится, т.к. тестовые объекты RealEstate не привязаны к пользователю admin
        System.out.println("  UserTestData.admin.getRealEstates() = " + UserTestData.admin.getRealEstates());
        REAL_ESTATE_MATCHER.assertMatch(UserTestData.admin.getRealEstates(), RealEstateTestData.adminRealEstates);
*/
    }

    @Test
    void getWithUserRealEstates() {
        User user = service.getWithRealEstates(USER_ID);
        USER_MATCHER.assertMatch(user, UserTestData.user);
        REAL_ESTATE_MATCHER.assertMatch(user.getRealEstates(), RealEstateTestData.realEstates);

/*
        //  ToDo:   Нужно привязать тестовые объекты RealEstate к соответствующим пользователям.
        //          Если раскомментировать, то тест завалится, т.к. тестовые объекты RealEstate не привязаны к пользователю user
        System.out.println("  UserTestData.user.getRealEstates() = " + UserTestData.user.getRealEstates());
        REAL_ESTATE_MATCHER.assertMatch(UserTestData.user.getRealEstates(), RealEstateTestData.realEstates);
*/
    }

    @Test
    public void getWithRealEstatesNotFound() {
        Assertions.assertThrows(NotFoundException.class,
                () -> service.getWithRealEstates(NOT_FOUND));
    }
}
