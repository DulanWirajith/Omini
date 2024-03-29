package lk.dbay.controller;

import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.entity.CustomerProfile;
import lk.dbay.service.CustomerProfileS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.CUSTOMER_PROFILE)
public class CustomerProfileController {

    @Autowired
    private CustomerProfileS customerProfileS;

    @PostMapping(value = "/addCustomerProfile")
    public ResponseEntity addCustomerProfile(@RequestPart("customerProfile") CustomerProfile customerProfile, @RequestParam(value = "imageFile", required = false) MultipartFile file) {
        try {
            CustomerProfileDTO customerProfileDTO = customerProfileS.addCustomerProfile(customerProfile, file);
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

    @PutMapping(value = CommonConstants.CUSTOMER + "/updateCustomerProfile/{customerProfileId}")
    public ResponseEntity updateCustomerProfile(@RequestPart("customerProfile") CustomerProfile customerProfile, @RequestParam(value = "imageFile", required = false) MultipartFile file, @PathVariable String customerProfileId) {
        try {
            CustomerProfileDTO customerProfileDTO = customerProfileS.updateCustomerProfile(customerProfile, file, customerProfileId);
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

    @GetMapping(value = CommonConstants.CUSTOMER + "/getCustomerProfile/{customerProfileId}")
    public ResponseEntity getCustomerProfile(@PathVariable String customerProfileId) {
        return ResponseEntity.ok(customerProfileS.getCustomerProfile(customerProfileId));
    }

//    @PostMapping(value = "/addCart")
//    public ResponseEntity addCart(@RequestBody ShopCart shopCart) {
//        try {
//            ShopCartDTO shopCartDTO = customerProfileS.addCart(shopCart);
//            if (shopCartDTO != null) {
//                return ResponseEntity.ok(shopCartDTO);
//            } else {
//                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
//            }
//        } catch (DataIntegrityViolationException e) {
//            return new ResponseEntity<>(e.getCause().getCause().getMessage().split("'")[3].replace('_', ' ') + " is already taken, Try again", HttpStatus.BAD_REQUEST);
//        } catch (Exception e) {
//            e.printStackTrace();
//            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//        }
//    }

//    @GetMapping(value = "/getCart/{cartId}")
//    public ResponseEntity getCart(@PathVariable String cartId) {
//        return ResponseEntity.ok(customerProfileS.getCart(cartId));
//    }
}
