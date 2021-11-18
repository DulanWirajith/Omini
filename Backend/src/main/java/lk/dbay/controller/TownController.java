package lk.dbay.controller;

import lk.dbay.service.TownS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.TOWN)
public class TownController {

    @Autowired
    private TownS townS;

    @GetMapping(value = CommonConstants.USER + "/getCountries")
    public ResponseEntity getCountries() {
        return ResponseEntity.ok(townS.getCountries());
    }

    @GetMapping(value = CommonConstants.USER + "/getDistricts/{countryId}")
    public ResponseEntity getDistricts(@PathVariable String countryId) {
        return ResponseEntity.ok(townS.getDistricts(countryId));
    }

    @GetMapping(value = CommonConstants.USER + "/getTowns/{districtId}")
    public ResponseEntity getTowns(@PathVariable String districtId) {
        return ResponseEntity.ok(townS.getTowns(districtId));
    }

    @GetMapping(value = CommonConstants.USER + "/getTownsWIthDistrict")
    public ResponseEntity getTownsWIthDistrict() {
        return ResponseEntity.ok(townS.getTownsWIthDistrict());
    }
}
