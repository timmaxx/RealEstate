package com.timmax.realestate.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import com.timmax.realestate.service.AbstractRealEstateServiceTest;

import static com.timmax.realestate.Profiles.JDBC;

@ActiveProfiles(JDBC)
class JdbcRealEstateServiceTest extends AbstractRealEstateServiceTest {
}
