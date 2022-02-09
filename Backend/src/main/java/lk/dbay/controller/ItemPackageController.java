package lk.dbay.controller;

import lk.dbay.dto.ItemPackageReviewDTO;
import lk.dbay.dto.ItemPackageReviewResponseDTO;
import lk.dbay.entity.item.ItemPackageReview;
import lk.dbay.entity.item.ItemPackageReviewResponse;
import lk.dbay.service.ItemPackageS;
import lk.dbay.service.ItemS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.ITEM_PACKAGE)
public class ItemPackageController {

    @Autowired
    private ItemPackageS itemPackageS;

//    @GetMapping(value = "/getItems")
//    public ResponseEntity getItems() {
//        return ResponseEntity.ok(itemPackageS.getItems());
//    }

    //    @GetMapping(value = "/getItemPackagesBusinessCategory/{businessProfileId}/{businessCategoryId}")
//    public ResponseEntity getItemPackagesBusinessCategory(@PathVariable String businessProfileId, @PathVariable String businessCategoryId) {
//        return ResponseEntity.ok(itemPackageS.getItemsPackageBusinessCategory(businessProfileId, businessCategoryId));
//    }
//
//    @GetMapping(value = "/getItemPackagesOrdered/{businessProfileId}/{businessCategoryId}/{start}/{limit}")
//    public ResponseEntity getItemPackagesOrdered(@PathVariable String businessProfileId, @PathVariable String businessCategoryId, @PathVariable int start, @PathVariable int limit) {
//        return ResponseEntity.ok(itemPackageS.getItemPackagesOrdered(businessProfileId, businessCategoryId, start, limit));
//    }
//
    @GetMapping(value = CommonConstants.SHOP + "/setItemPackageAvailable/{itemId}")
    public ResponseEntity setItemPackageAvailable(@PathVariable String itemId) {
        return ResponseEntity.ok(itemPackageS.setItemPackageAvailable(itemId));
    }

    @GetMapping(value = CommonConstants.CUSTOMER + "/setItemPackageFavourite/{customerId}/{itemId}")
    public ResponseEntity setItemPackageFavourite(@PathVariable String customerId, @PathVariable String itemId) {
        return ResponseEntity.ok(itemPackageS.setItemPackageFavourite(customerId, itemId));
    }

    @GetMapping(value = CommonConstants.CUSTOMER + "/getFavItemPackages/{customerId}")
    public ResponseEntity getFavItemPackages(@PathVariable String customerId) {
        return ResponseEntity.ok(itemPackageS.getFavItemPackages(customerId));
    }

    @GetMapping(value = "/getItemsPackagesBySearch/{txt}/{category}/{customerId}")
    public ResponseEntity getItemsPackagesBySearch(@PathVariable String txt, @PathVariable String category, @PathVariable String customerId) {
        return ResponseEntity.ok(itemPackageS.getItemsPackagesBySearch(txt, category, customerId));
    }

    @GetMapping(value = "/getItemPackageSelected/{itemId}/{type}/{customerId}")
    public ResponseEntity getItemPackageSelected(@PathVariable String itemId, @PathVariable String type, @PathVariable String customerId) {
        return ResponseEntity.ok(itemPackageS.getItemPackageSelected(itemId, type, customerId));
    }

    // Item Review

    @PostMapping(value = CommonConstants.CUSTOMER + "/addItemPackageReview")
    public ResponseEntity addItemPackageReview(@RequestBody ItemPackageReview itemPackageReview) {
        try {
            ItemPackageReviewDTO itemPackageReviewDTO = itemPackageS.addItemPackageReview(itemPackageReview);
            if (itemPackageReviewDTO != null) {
                return ResponseEntity.ok(itemPackageReviewDTO);
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

    @PostMapping(value = CommonConstants.CUSTOMER + "/addItemPackageResponse")
    public ResponseEntity addItemPackageResponse(@RequestBody ItemPackageReviewResponse itemReviewResponse) {
        try {
            ItemPackageReviewResponseDTO itemReviewResponseDTO = itemPackageS.addItemPackageResponse(itemReviewResponse);
            if (itemReviewResponseDTO != null) {
                return ResponseEntity.ok(itemReviewResponseDTO);
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

    @GetMapping(value = "/getItemPackageReviews/{itemId}/{customerId}")
    public ResponseEntity getItemPackageReviews(@PathVariable String itemId, @PathVariable String customerId) {
        return ResponseEntity.ok(itemPackageS.getItemPackageReviews(itemId, customerId));
    }

//    @GetMapping(value = "/itemImg/{id}")
//    public ResponseEntity<byte[]> getItemImg(@PathVariable String id) {
//
//        ItemImgDTO itemImg = itemS.getItemImg(id);
//
//        if (itemImg != null) {
//            return ResponseEntity.ok()
//                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + itemImg.getItemImgName() + "\"")
//                    .body(itemImg.getItemImg());
//        }
//        return null;
//    }
}