package com.timmax.realestate.web.realEstate;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.timmax.realestate.model.RealEstate;
import com.timmax.realestate.dto.RealEstateDto;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(
        value = RealEstateRestController.REST_URL,
        produces = MediaType.APPLICATION_JSON_VALUE
)
public class RealEstateRestController extends AbstractRealEstateController {
    static final String REST_URL = "/rest/profile/realEstates";

    @Override
    @GetMapping("/{id}")
    public RealEstate get(@PathVariable int id) {
        return super.get(id);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable int id) {
        super.delete(id);
    }

    @Override
    @GetMapping
    public List<RealEstateDto> getAll() {
        return super.getAll();
    }

    @Override
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@RequestBody RealEstate realEstate, @PathVariable int id) {
        super.update(realEstate, id);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<RealEstate> createWithLocation(@RequestBody RealEstate realEstate) {
        RealEstate created = super.create(realEstate);

        URI uriOfNewResource = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path(REST_URL + "/{id}")
                .buildAndExpand(created.getId()).toUri();

        return ResponseEntity.created(uriOfNewResource).body(created);
    }

    @GetMapping("/between")
    public List<RealEstateDto> getBetween(
            @RequestParam Float startSquare,
            @RequestParam Float endSquare) {
        return super.getBetween(startSquare, endSquare);
    }
}
