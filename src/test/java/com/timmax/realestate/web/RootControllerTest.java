package com.timmax.realestate.web;

import org.assertj.core.matcher.AssertionMatcher;
import org.junit.jupiter.api.Test;
import com.timmax.realestate.model.User;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static com.timmax.realestate.RealEstateTestData.realEstates;
import static com.timmax.realestate.UserTestData.*;
import static com.timmax.realestate.util.RealEstateUtil.getDtos;

class RootControllerTest extends AbstractControllerTest {

    @Test
    void getUsers() throws Exception {
        perform(get("/users"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("users"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/users.jsp"))
                .andExpect(model().attribute("users",
                        //  Здесь матчер будет работать с сущностями
                        //  (они сохраняются и достаётся из БД ч/з JPA Hibernate)
                        //  и мы не можем переопределять equals и hashCode на основе всех полей,
                        //  т.к. Hibernate будет работать не верно.
                        //  (У сущностей можно переопределять equals и hashCode только на основе неизменяемых полей)
                        new AssertionMatcher<List<User>>() {
                            @Override
                            public void assertion(List<User> actual) throws AssertionError {
                                USER_MATCHER.assertMatch(actual, admin, guest, user);
                            }
                        }
                ));
    }

    @Test
    void getRealEstates() throws Exception {
        perform(get("/realEstates"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("realEstates"))
                .andExpect(forwardedUrl("/WEB-INF/jsp/realEstates.jsp"))
                .andExpect(model().attribute("realEstates",
                        //  Здесь мы переводим сущности в Dto, а в Dto можно определить equals и hashCode.
                        getDtos(realEstates)
                ));
    }
}
