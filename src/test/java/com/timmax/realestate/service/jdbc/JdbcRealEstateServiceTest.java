package com.timmax.realestate.service.jdbc;

import org.junit.Ignore;
import org.springframework.test.context.ActiveProfiles;
import com.timmax.realestate.service.AbstractRealEstateServiceTest;

import static com.timmax.realestate.Profiles.JDBC;

@ActiveProfiles(JDBC)
public class JdbcRealEstateServiceTest extends AbstractRealEstateServiceTest {
    @Override
    @Ignore
    public void createWithException() {
    }
}
