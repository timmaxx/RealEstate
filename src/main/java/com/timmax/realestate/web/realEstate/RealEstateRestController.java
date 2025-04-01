package com.timmax.realestate.web.realEstate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.service.RealEstateService;
import com.timmax.realestate.dto.RealEstateDto;
import com.timmax.realestate.util.RealEstateUtil;
import com.timmax.realestate.web.SecurityUtil;

import java.util.List;

import static com.timmax.realestate.util.ValidationUtil.assureIdConsistent;
import static com.timmax.realestate.util.ValidationUtil.checkIsNew;

@Controller
public class RealEstateRestController {
    private static final Logger log = LoggerFactory.getLogger(RealEstateRestController.class);

    private final RealEstateService service;

    public RealEstateRestController(RealEstateService service) {
        this.service = service;
    }

    public RealEstate get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get real estate {} for user {}", id, userId);
        return service.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete real estate {} for user {}", id, userId);
        service.delete(id, userId);
    }

    public List<RealEstateDto> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return RealEstateUtil.getDtos(service.getAll(userId));
    }

    public RealEstate create(RealEstate realEstate) {
        int userId = SecurityUtil.authUserId();
        checkIsNew(realEstate);
        log.info("create {} for user {}", realEstate, userId);
        return service.create(realEstate, userId);
    }

    public void update(RealEstate realEstate, int id) {
        int userId = SecurityUtil.authUserId();
        assureIdConsistent(realEstate, id);
        log.info("update {} for user {}", realEstate, userId);
        service.update(realEstate, userId);
    }
}
