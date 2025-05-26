package com.timmax.realestate.service;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.rules.ExternalResource;
import org.junit.rules.Stopwatch;
import org.junit.runner.RunWith;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;

import com.timmax.realestate.ActiveDbProfileResolver;
import com.timmax.realestate.TimingRules;

import static org.junit.Assert.assertThrows;
import static com.timmax.realestate.util.ValidationUtil.getRootCause;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(
        scripts = "classpath:db/populateDB.sql",
        config = @SqlConfig(encoding = "UTF-8")
)
@ActiveProfiles(resolver = ActiveDbProfileResolver.class)
public abstract class AbstractServiceTest {
    @ClassRule
    public static ExternalResource summary = TimingRules.SUMMARY;

    @Rule
    public Stopwatch stopwatch = TimingRules.STOPWATCH;

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
