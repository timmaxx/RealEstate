package com.timmax.realestate.service;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import com.timmax.realestate.Profiles;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.util.exception.NotFoundException;

import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertThrows;
import static org.slf4j.LoggerFactory.getLogger;
import static com.timmax.realestate.RealEstateTestData.*;
import static com.timmax.realestate.UserTestData.ADMIN_ID;
import static com.timmax.realestate.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(
        scripts = "classpath:db/populateDB.sql",
        config = @SqlConfig(encoding = "UTF-8")
)
@ActiveProfiles(Profiles.ACTIVE_DB)
public class RealEstateServiceTest {
    private static final Logger log = getLogger("result");

    private static final StringBuilder results = new StringBuilder();

    @Rule
    // http://stackoverflow.com/questions/14892125/what-is-the-best-practice-to-determine-the-execution-time-of-the-bussiness-relev
    public final Stopwatch stopwatch = new Stopwatch() {
        @Override
        protected void finished(long nanos, Description description) {
            String result = String.format("\n%-25s %7d", description.getMethodName(), TimeUnit.NANOSECONDS.toMillis(nanos));
            results.append(result);
            log.info(result + " ms\n");
        }
    };

    @Autowired
    private RealEstateService service;

    @AfterClass
    public static void printResult() {
        log.info("""
                
                ---------------------------------
                Test                 Duration, ms
                ---------------------------------""" +
                results + """
                
                ---------------------------------"""
        );
    }

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
        RealEstate newMeal = getNew();
        newMeal.setId(newId);
        REAL_ESTATE_MATCHER.assertMatch(created, newMeal);
        REAL_ESTATE_MATCHER.assertMatch(service.get(newId, USER_ID), newMeal);
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
        NotFoundException exception = assertThrows(NotFoundException.class, () -> service.update(realEstate1, ADMIN_ID));
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
}
