package com.timmax.realestate.web.realEstate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.service.RealEstateService;
import com.timmax.realestate.dto.RealEstateDto;
import com.timmax.realestate.util.RealEstateUtil;
import com.timmax.realestate.web.SecurityUtil;

import java.util.List;

import static com.timmax.realestate.util.ValidationUtil.assureIdConsistent;
import static com.timmax.realestate.util.ValidationUtil.checkIsNew;

public abstract class AbstractRealEstateController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private RealEstateService realEstateService;

    public RealEstate get(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("get realEstate {} for user {}", id, userId);
        return realEstateService.get(id, userId);
    }

    public void delete(int id) {
        int userId = SecurityUtil.authUserId();
        log.info("delete realEstate {} for user {}", id, userId);
        realEstateService.delete(id, userId);
    }

    public List<RealEstateDto> getAll() {
        int userId = SecurityUtil.authUserId();
        log.info("getAll for user {}", userId);
        return RealEstateUtil.getDtos(realEstateService.getAll(userId));
    }

    public RealEstate create(RealEstate realEstate) {
        int userId = SecurityUtil.authUserId();
        log.info("create {} for user {}", realEstate, userId);
        checkIsNew(realEstate);
        return realEstateService.create(realEstate, userId);
    }

    public void update(RealEstate realEstate, int id) {
        int userId = SecurityUtil.authUserId();
        log.info("update {} for user {}", realEstate, userId);
        assureIdConsistent(realEstate, id);
        realEstateService.update(realEstate, userId);
    }

    /**
     * <ol>Filter separately
     * <li>by date</li>
     * <li>by time for every date</li>
     * </ol>
     */
    public List<RealEstateDto> getBetween(
            @Nullable Float starSquare,
            @Nullable Float endSquare
    ) {
        int userId = SecurityUtil.authUserId();
        log.info("getBetween squares({} - {}) for user {}", starSquare, endSquare, userId);

        List<RealEstate> realEstatesDateFiltered = realEstateService.getBetweenInclusive(
                starSquare, endSquare,
                userId
        );
        return RealEstateUtil.getDtos(realEstatesDateFiltered);
    }
}
