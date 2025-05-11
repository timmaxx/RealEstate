package com.timmax.realestate.service.jdbc;

import org.junit.Ignore;
import org.springframework.test.context.ActiveProfiles;
import com.timmax.realestate.service.AbstractUserServiceTest;

import static com.timmax.realestate.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcUserServiceTest extends AbstractUserServiceTest {
    @Override
    @Ignore
    public void createWithException() {
    }
}
