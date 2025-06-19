package com.timmax.realestate.web.realEstate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.service.RealEstateService;
import com.timmax.realestate.util.exception.NotFoundException;
import com.timmax.realestate.web.AbstractControllerTest;
import com.timmax.realestate.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.timmax.realestate.RealEstateTestData.*;
import static com.timmax.realestate.UserTestData.USER_ID;
import static com.timmax.realestate.util.RealEstateUtil.createDto;
import static com.timmax.realestate.util.RealEstateUtil.getDtos;

public class RealEstateRestControllerTest extends AbstractControllerTest {
    private static final String REST_URL = RealEstateRestController.REST_URL + '/';

    @Autowired
    private RealEstateService realEstateService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + REAL_ESTATE1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(REAL_ESTATE_MATCHER.contentJson(realEstate1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + REAL_ESTATE1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> realEstateService.get(REAL_ESTATE1_ID, USER_ID));
    }

    @Test
    void update() throws Exception {
        RealEstate updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + REAL_ESTATE1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        REAL_ESTATE_MATCHER.assertMatch(realEstateService.get(REAL_ESTATE1_ID, USER_ID), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        RealEstate newRealEstate = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newRealEstate)))
                .andExpect(status().isCreated());

        RealEstate created = REAL_ESTATE_MATCHER.readFromJson(action);
        int newId = created.id();
        newRealEstate.setId(newId);
        REAL_ESTATE_MATCHER.assertMatch(created, newRealEstate);
        REAL_ESTATE_MATCHER.assertMatch(realEstateService.get(newId, USER_ID), newRealEstate);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(DTO_MATCHER.contentJson(getDtos(realEstates)));
    }

    @Test
    void getBetween() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "between?startSquare=20&endSquare=40"))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(DTO_MATCHER.contentJson(createDto(realEstate2), createDto(realEstate3)));
    }
}
