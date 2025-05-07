package com.timmax.realestate.service.datajpa;

import com.timmax.realestate.service.AbstractRealEstateServiceTest;
import org.springframework.test.context.ActiveProfiles;

import static com.timmax.realestate.Profiles.DATAJPA;

@ActiveProfiles(DATAJPA)
public class DataJpaRealEstateServiceTest extends AbstractRealEstateServiceTest {
}
