package com.timmax.realestate.service.jpa;

import org.springframework.test.context.ActiveProfiles;
import com.timmax.realestate.service.AbstractUserServiceTest;

import static com.timmax.realestate.Profiles.JPA;

@ActiveProfiles(JPA)
class JpaUserServiceTest extends AbstractUserServiceTest {
}
