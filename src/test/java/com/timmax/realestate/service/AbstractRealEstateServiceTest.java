package com.timmax.realestate.service;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.util.exception.NotFoundException;

import javax.validation.ConstraintViolationException;

import static org.junit.Assert.assertThrows;
import static com.timmax.realestate.RealEstateTestData.*;
import static com.timmax.realestate.UserTestData.ADMIN_ID;
import static com.timmax.realestate.UserTestData.USER_ID;

public abstract class AbstractRealEstateServiceTest extends AbstractServiceTest {

    @Autowired
    protected RealEstateService service;

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
        int newId = created.id();
        RealEstate newRealEstate = getNew();
        newRealEstate.setId(newId);
        REAL_ESTATE_MATCHER.assertMatch(created, newRealEstate);
        REAL_ESTATE_MATCHER.assertMatch(service.get(newId, USER_ID), newRealEstate);
    }

    @Test
    public void duplicateAddressCreate() {
        assertThrows(DataAccessException.class, () ->
                service.create(new RealEstate(null, realEstate1.getAddress(), 100), USER_ID));
    }

    @Test
    public void get() {
        RealEstate actual = service.get(ADMIN_REAL_ESTATE_ID, ADMIN_ID);
        REAL_ESTATE_MATCHER.assertMatch(actual, adminRealEstate1);
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
        REAL_ESTATE_MATCHER.assertMatch(service.get(REAL_ESTATE1_ID, USER_ID), getUpdated());
    }

    @Test
    public void updateNotOwn() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(getUpdated(), ADMIN_ID));
        Assert.assertEquals("Not found entity with id=" + REAL_ESTATE1_ID, exception.getMessage());
        REAL_ESTATE_MATCHER.assertMatch(service.get(REAL_ESTATE1_ID, USER_ID), realEstate1);
    }

    @Test
    public void getAll() {
        REAL_ESTATE_MATCHER.assertMatch(service.getAll(USER_ID), realEstates);
    }

    @Test
    public void getBetweenInclusive() {
        REAL_ESTATE_MATCHER.assertMatch(service.getBetweenInclusive(
                        10F,
                        41F, USER_ID),
                realEstate1, realEstate2, realEstate3, realEstate4);
    }

    @Test
    public void getBetweenWithNullSquares() {
        REAL_ESTATE_MATCHER.assertMatch(service.getBetweenInclusive(null, null, USER_ID), realEstates);
    }

    @Test
    public void createWithException() {
        validateRootCause(ConstraintViolationException.class, () -> service.create(new RealEstate(null, "", 300), USER_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new RealEstate(null, "a", 300), USER_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new RealEstate(null, "  ", 300), USER_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new RealEstate(null, "a".repeat(121), 300), USER_ID));
        validateRootCause(ConstraintViolationException.class, () -> service.create(new RealEstate(null, "ab", 0), USER_ID));
    }
}
