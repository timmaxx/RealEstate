package com.timmax.realestate.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.util.exception.NotFoundException;

import static org.junit.Assert.assertThrows;
import static com.timmax.realestate.RealEstateTestData.*;
import static com.timmax.realestate.UserTestData.ADMIN_ID;
import static com.timmax.realestate.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class RealEstateServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private RealEstateService service;

    @Test
    public void delete() {
        service.delete(REAL_ESTATE1_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(REAL_ESTATE1_ID, USER_ID));
    }

    @Test
    public void deleteNotFound() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotOwn() {
        assertThrows(NotFoundException.class, () -> service.delete(REAL_ESTATE1_ID, ADMIN_ID));
    }

    @Test
    public void create() {
        RealEstate created = service.create(getNew(), USER_ID);
        int newId = created.getId();
        RealEstate newMeal = getNew();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateAddressCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new RealEstate(null, realEstate1.getAddress(), 100), USER_ID));
    }

    @Test
    public void get() {
        RealEstate actual = service.get(ADMIN_REAL_ESTATE_ID, ADMIN_ID);
        assertMatch(actual, adminRealEstate1);
    }

    @Test
    public void getNotFound() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotOwn() {
        assertThrows(NotFoundException.class, () -> service.get(REAL_ESTATE1_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        RealEstate updated = getUpdated();
        service.update(updated, USER_ID);
        assertMatch(service.get(REAL_ESTATE1_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwn() {
        assertThrows(NotFoundException.class, () -> service.update(realEstate1, ADMIN_ID));
        assertMatch(service.get(REAL_ESTATE1_ID, USER_ID), realEstate1);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), realEstates);
    }

    @Test
    public void getBetweenInclusive() {
        assertMatch(service.getBetweenInclusive(
                        10F,
                        41F, USER_ID),
                realEstate1, realEstate2, realEstate3, realEstate4);
    }

    @Test
    public void getBetweenWithNullSquares() {
        assertMatch(service.getBetweenInclusive(null, null, USER_ID), realEstates);
    }
}
