package lk.dbay.controller;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.entity.BusinessProfile;
import lk.dbay.entity.CustomerProfile;
import lk.dbay.service.BusinessProfileS;
import lk.dbay.service.CustomerProfileS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.CUSTOMER_PROFILE)
public class CustomerProfileController {

    @Autowired
    private CustomerProfileS customerProfileS;

    @PostMapping(value = "/addCustomerProfile")
    public ResponseEntity addCustomerProfile(@RequestBody CustomerProfile customerProfile) {
        try {
            CustomerProfileDTO customerProfileDTO = customerProfileS.addCustomerProfile(customerProfile);
            if (customerProfileDTO != null) {
                return ResponseEntity.ok(customerProfileDTO);
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

    @PutMapping(value = "/updateCustomerProfile/{customerProfileId}")
    public ResponseEntity updateCustomerProfile(@RequestBody CustomerProfile customerProfile, @PathVariable String customerProfileId) {
        try {
            CustomerProfileDTO customerProfileDTO = customerProfileS.updateCustomerProfile(customerProfile, customerProfileId);
            if (customerProfileDTO != null) {
                return ResponseEntity.ok(customerProfileDTO);
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

    @GetMapping(value = "/getCustomerProfile/{customerProfileId}")
    public ResponseEntity getCustomerProfile(@PathVariable String customerProfileId) {
        return ResponseEntity.ok(customerProfileS.getCustomerProfile(customerProfileId));
    }
}
