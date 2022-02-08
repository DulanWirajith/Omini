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
    public ResponseEntity addBusinessProfile(@RequestPart("businessProfile") BusinessProfile businessProfile, @RequestParam("imageFile") MultipartFile[] files) {
        try {
            BusinessProfileDTO businessProfileDTO = businessProfileS.addBusinessProfile(businessProfile, files);
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

    @PutMapping(value = "/updateBusinessProfile/{businessProfileId}")
    public ResponseEntity updateBusinessProfile(@RequestPart("businessProfile") BusinessProfile businessProfile, @RequestParam("imageFile") MultipartFile[] files, @PathVariable String businessProfileId) {
        try {
            BusinessProfileDTO businessProfileDTO = businessProfileS.updateBusinessProfile(businessProfile, files, businessProfileId);
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

    @GetMapping(value = "/getBusinessProfile/{businessProfileId}/{needItems}/{customerId}")
    public ResponseEntity getBusinessProfile(@PathVariable String businessProfileId, @PathVariable boolean needItems, @PathVariable String customerId) {
        return ResponseEntity.ok(businessProfileS.getBusinessProfile(businessProfileId, needItems, customerId));
    }

    @GetMapping(value = "/getItemsBusinessProfile/{businessProfileId}/{categoryId}/{customerId}")
    public ResponseEntity getItemsBusinessProfile(@PathVariable String businessProfileId, @PathVariable String categoryId, @PathVariable String customerId) {
        return ResponseEntity.ok(businessProfileS.getItemsBusinessProfile(businessProfileId, categoryId, customerId));
    }

    @GetMapping(value = "/followBusiness/{customerId}/{businessProId}")
    public ResponseEntity followBusiness(@PathVariable String customerId, @PathVariable String businessProId) {
        return ResponseEntity.ok(businessProfileS.followBusiness(customerId, businessProId));
    }

    @GetMapping(value = "/getFollowedBusinesses/{customerId}")
    public ResponseEntity getFollowedBusinesses(@PathVariable String customerId) {
        return ResponseEntity.ok(businessProfileS.getFollowedBusinesses(customerId));
    }

    //

    @PostMapping(value = "/addBusinessReview")
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

    @PostMapping(value = "/addBusinessReviewResponse")
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
