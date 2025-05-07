package com.timmax.realestate.service.datajpa;

import org.springframework.test.context.ActiveProfiles;
import com.timmax.realestate.service.AbstractUserServiceTest;

import static com.timmax.realestate.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJpaUserServiceTest extends AbstractUserServiceTest {
}
