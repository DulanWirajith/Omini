package lk.dbay.controller;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.entity.BusinessProfile;
import lk.dbay.service.BusinessProfileS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.BUSINESS_PROFILE)
public class BusinessProfileController {

    @Autowired
    private BusinessProfileS businessProfileS;

    @PostMapping(value = CommonConstants.USER + CommonConstants.ADD)
    public ResponseEntity addBusinessProfile(@RequestBody BusinessProfile businessProfile) {
        try {
            BusinessProfileDTO businessProfileDTO = businessProfileS.addBusinessProfile(businessProfile);
            if (businessProfileDTO != null) {
                return ResponseEntity.ok(businessProfileDTO);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getCause().getCause().getMessage().split("'")[3].replace('_', ' ') + " is already taken, Try again", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
