package com.timmax.realestate.web;

import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.timmax.realestate.MatcherFactory.contentStringAsTextPlain;
import static com.timmax.realestate.MatcherFactory.contentStringAsJson;
import static com.timmax.realestate.web.RootRestController.TEXT_IN_RUSSIAN;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class RootRestControllerTest extends AbstractControllerTest {
    @Test
    void getTextPlain() throws Exception {
        perform(MockMvcRequestBuilders.get(RootRestController.REST_URL + "/textUtfByRussianAsTextPlain"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.TEXT_PLAIN))
                .andExpect(contentStringAsTextPlain(TEXT_IN_RUSSIAN))
        ;
    }

    @Test
    void getJson() throws Exception {
        perform(MockMvcRequestBuilders.get(RootRestController.REST_URL + "/textUtfByRussianAsJson"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(contentStringAsJson(TEXT_IN_RUSSIAN))
        ;
    }
}
