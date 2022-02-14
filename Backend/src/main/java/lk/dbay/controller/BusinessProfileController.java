package lk.dbay.controller;

import lk.dbay.dto.*;
import lk.dbay.entity.BusinessProfile;
import lk.dbay.entity.BusinessReview;
import lk.dbay.entity.BusinessReviewResponse;
import lk.dbay.service.BusinessProfileS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.BUSINESS_PROFILE)
public class BusinessProfileController {

    @Autowired
    private BusinessProfileS businessProfileS;

    @PostMapping(value = "/addBusinessProfile")
    public ResponseEntity addBusinessProfile(@RequestPart("businessProfile") BusinessProfile businessProfile, @RequestParam(value = "imageFile", required = false) MultipartFile file) {
        try {
            BusinessProfileDTO businessProfileDTO = businessProfileS.addBusinessProfile(businessProfile, file);
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

    @PutMapping(value = CommonConstants.SHOP + "/updateBusinessProfile/{businessProfileId}")
    public ResponseEntity updateBusinessProfile(@RequestPart("businessProfile") BusinessProfile businessProfile, @RequestParam(value = "imageFile", required = false) MultipartFile file, @PathVariable String businessProfileId) {
        try {
            BusinessProfileDTO businessProfileDTO = businessProfileS.updateBusinessProfile(businessProfile, file, businessProfileId);
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

    @GetMapping(value = "/getBusinessProfile/{businessProfileId}/{needItems}/{customerId}/{type}")
    public ResponseEntity getBusinessProfile(@PathVariable String businessProfileId, @PathVariable boolean needItems, @PathVariable String customerId, @PathVariable String type) {
        return ResponseEntity.ok(businessProfileS.getBusinessProfile(businessProfileId, needItems, customerId, type));
    }

    @GetMapping(value = "/getItemsBusinessProfile/{businessProfileId}/{categoryId}/{customerId}/{type}")
    public ResponseEntity getItemsBusinessProfile(@PathVariable String businessProfileId, @PathVariable String categoryId, @PathVariable String customerId, @PathVariable String type) {
        return ResponseEntity.ok(businessProfileS.getItemsBusinessProfile(businessProfileId, categoryId, customerId, type));
    }

    @GetMapping(value = "/getShopsBySearch/{txt}/{category}/{district}/{town}/{customerId}")
    public ResponseEntity getShopsBySearch(@PathVariable String txt, @PathVariable String category, @PathVariable String district, @PathVariable String town, @PathVariable String customerId) {
        return ResponseEntity.ok(businessProfileS.getShopsBySearch(txt, category, district, town, customerId));
    }

    @GetMapping(value = CommonConstants.CUSTOMER + "/followBusiness/{customerId}/{businessProId}")
    public ResponseEntity followBusiness(@PathVariable String customerId, @PathVariable String businessProId) {
        return ResponseEntity.ok(businessProfileS.followBusiness(customerId, businessProId));
    }

    @GetMapping(value = CommonConstants.CUSTOMER + "/getFollowedBusinesses/{customerId}")
    public ResponseEntity getFollowedBusinesses(@PathVariable String customerId) {
        return ResponseEntity.ok(businessProfileS.getFollowedBusinesses(customerId));
    }

    //

    @PostMapping(value = CommonConstants.CUSTOMER + "/addBusinessReview")
    public ResponseEntity addBusinessReview(@RequestBody BusinessReview itemPackageReview) {
        try {
            BusinessReviewDTO businessReviewDTO = businessProfileS.addBusinessReview(itemPackageReview);
            if (businessReviewDTO != null) {
                return ResponseEntity.ok(businessReviewDTO);
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

    @PostMapping(value = CommonConstants.CUSTOMER + "/addBusinessReviewResponse")
    public ResponseEntity addBusinessReviewResponse(@RequestBody BusinessReviewResponse itemReviewResponse) {
        try {
            BusinessReviewResponseDTO reviewResponseDTO = businessProfileS.addBusinessReviewResponse(itemReviewResponse);
            if (reviewResponseDTO != null) {
                return ResponseEntity.ok(reviewResponseDTO);
            } else {
                return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
            }
        } catch (DataIntegrityViolationException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<>(e.getCause().getCause().getMessage().split("'")[3].replace('_', ' ') + " is already taken, Try again", HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getBusinessReviews/{businessId}/{customerId}")
    public ResponseEntity getBusinessReviews(@PathVariable String businessId, @PathVariable String customerId) {
        return ResponseEntity.ok(businessProfileS.getBusinessReviews(businessId, customerId));
    }
}
