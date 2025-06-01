package com.timmax.realestate.web.json;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.timmax.realestate.model.RealEstate;

import java.util.List;

import static com.timmax.realestate.RealEstateTestData.*;

class JsonUtilTest {
    private static final Logger log = LoggerFactory.getLogger(JsonUtilTest.class);

    @Test
    void readWriteValue() {
        String json = JsonUtil.writeValue(adminRealEstate1);
        log.info(json);
        RealEstate actualRealEstate = JsonUtil.readValue(json, RealEstate.class);
        REAL_ESTATE_MATCHER.assertMatch(actualRealEstate, adminRealEstate1);
    }

    @Test
    void readWriteValues() {
        String json = JsonUtil.writeValue(realEstates);
        log.info(json);
        List<RealEstate> actualListOfRealEstates = JsonUtil.readValues(json, RealEstate.class);
        REAL_ESTATE_MATCHER.assertMatch(actualListOfRealEstates, realEstates);
    }
}
