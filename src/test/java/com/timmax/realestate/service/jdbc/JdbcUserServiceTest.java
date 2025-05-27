package com.timmax.realestate.service.jdbc;

import org.springframework.test.context.ActiveProfiles;
import com.timmax.realestate.service.AbstractUserServiceTest;

import static com.timmax.realestate.Profiles.JDBC;

@ActiveProfiles(JDBC)
class JdbcUserServiceTest extends AbstractUserServiceTest {
}
