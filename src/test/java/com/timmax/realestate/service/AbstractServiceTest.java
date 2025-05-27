package com.timmax.realestate.service;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.timmax.realestate.ActiveDbProfileResolver;
import com.timmax.realestate.TimingExtension;

import static org.junit.jupiter.api.Assertions.assertThrows;

import static com.timmax.realestate.util.ValidationUtil.getRootCause;

@SpringJUnitConfig(locations = {
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
@Sql(
        scripts = "classpath:db/populateDB.sql",
        config = @SqlConfig(encoding = "UTF-8")
)
@ExtendWith(TimingExtension.class)
public abstract class AbstractServiceTest {

    //  Check root cause in JUnit: https://github.com/junit-team/junit4/pull/778
    protected <T extends Throwable> void validateRootCause(Runnable runnable) {
        assertThrows((Class<T>) javax.validation.ConstraintViolationException.class, () -> {
            try {
                runnable.run();
            } catch (Exception e) {
                throw getRootCause(e);
            }
        });
    }
}
