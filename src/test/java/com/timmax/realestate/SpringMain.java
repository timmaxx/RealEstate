package com.timmax.realestate;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import com.timmax.realestate.model.Role;
import com.timmax.realestate.model.User;
import com.timmax.realestate.dto.RealEstateDto;
import com.timmax.realestate.web.realEstate.RealEstateRestController;
import com.timmax.realestate.web.user.AdminRestController;

import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 automatic resource management (ARM)
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/inmemory.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email@mail.ru", "password", Role.ADMIN));

            System.out.println();

            RealEstateRestController realEstateController = appCtx.getBean(RealEstateRestController.class);
            List<RealEstateDto> filteredAllRealEstate =
/*
                    realEstateController.getBetween(
                            LocalDate.of(2020, Month.JANUARY, 30), LocalTime.of(7, 0),
                            LocalDate.of(2020, Month.JANUARY, 31), LocalTime.of(11, 0)
                    );
*/
                    realEstateController.getAll();
            filteredAllRealEstate.forEach(System.out::println);
            System.out.println();
            // System.out.println(realEstateController.getBetween(null, null, null, null));
            System.out.println(realEstateController.getAll());
        }
    }
}
