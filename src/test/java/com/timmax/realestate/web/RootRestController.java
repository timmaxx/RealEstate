package com.timmax.realestate.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(
        value = RootRestController.REST_URL
)
public class RootRestController {
    public static final String REST_URL = "/rest";
    public static final String TEXT_IN_RUSSIAN = "Текст на русском языке. Text in english.";

    protected final Logger log = LoggerFactory.getLogger(getClass());

    @RequestMapping(
            value = "/textUtfByRussianAsTextPlain",
            produces = MediaType.TEXT_PLAIN_VALUE
    )
    public String textUtfByRussianAsTextPlain() {
        log.info("textUtfByRussianAsTextPlain");
        return TEXT_IN_RUSSIAN;
    }

    @RequestMapping(
            value = "/textUtfByRussianAsJson",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public String textUtfByRussianAsJson() {
        log.info("textUtfByRussianAsJson");
        return TEXT_IN_RUSSIAN;
    }
}
