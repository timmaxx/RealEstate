package com.timmax.realestate.web.realEstate;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = RealEstateRestController.REST_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class RealEstateRestController extends AbstractRealEstateController {
    static final String REST_URL = "/rest/profile/realEstates";
}
