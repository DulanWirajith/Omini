package lk.dbay.controller;

import lk.dbay.dto.ItemDTO;
import lk.dbay.dto.ItemReviewDTO;
import lk.dbay.dto.ItemReviewResponseDTO;
import lk.dbay.entity.Item;
import lk.dbay.entity.ItemReview;
import lk.dbay.entity.ItemReviewResponse;
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
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.ITEM)
public class ItemController {

    @Autowired
    private ItemS itemS;

    @PostMapping(value = "/addItem")
    public ResponseEntity addItem(@RequestPart("item") Item item, @RequestParam("imageFile") MultipartFile[] files) {
        try {
            ItemDTO itemDTO = itemS.addItem(item, files);
            if (itemDTO != null) {
                return ResponseEntity.ok(itemDTO);
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

    @PutMapping(value = "/updateItem/{itemId}")
    public ResponseEntity updateItem(@RequestPart("item") Item item, @RequestParam("imageFile") MultipartFile[] files, @PathVariable String itemId) {
        try {
            ItemDTO itemDTO = itemS.updateItem(item, files, itemId);
            if (itemDTO != null) {
                return ResponseEntity.ok(itemDTO);
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

    @GetMapping(value = "/getItems")
    public ResponseEntity getItems() {
        return ResponseEntity.ok(itemS.getItems());
    }

    @GetMapping(value = "/getItemsBusinessCategory/{businessProfileId}/{businessCategoryId}")
    public ResponseEntity getItemsBusinessCategory(@PathVariable String businessProfileId, @PathVariable String businessCategoryId) {
        return ResponseEntity.ok(itemS.getItemsBusinessCategory(businessProfileId, businessCategoryId));
    }

    @GetMapping(value = "/getItemsOrdered/{businessProfileId}/{businessCategoryId}/{start}/{limit}")
    public ResponseEntity getItemsOrdered(@PathVariable String businessProfileId, @PathVariable String businessCategoryId, @PathVariable int start, @PathVariable int limit) {
        return ResponseEntity.ok(itemS.getItemsOrdered(businessProfileId, businessCategoryId, start, limit));
    }

    @GetMapping(value = "/setItemAvailable/{itemId}")
    public ResponseEntity setItemAvailable(@PathVariable String itemId) {
        return ResponseEntity.ok(itemS.setItemAvailable(itemId));
    }

    @GetMapping(value = "/getItemSelected/{itemId}")
    public ResponseEntity getItemSelected(@PathVariable String itemId) {
        return ResponseEntity.ok(itemS.getItemSelected(itemId));
    }

    @GetMapping(value = "/getItemsPackagesBySearch/{txt}/{category}")
    public ResponseEntity getItemsPackagesBySearch(@PathVariable String txt, @PathVariable String category) {
        return ResponseEntity.ok(itemS.getItemsPackagesBySearch(txt, category));
    }

    // Item Review

    @PostMapping(value = "/addItemReview")
    public ResponseEntity addItemReview(@RequestBody ItemReview itemReview) {
        try {
            ItemReviewDTO itemReviewDTO = itemS.addItemReview(itemReview);
            if (itemReviewDTO != null) {
                return ResponseEntity.ok(itemReviewDTO);
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

    @PostMapping(value = "/addItemResponse")
    public ResponseEntity addItemReviewResponse(@RequestBody ItemReviewResponse itemReviewResponse) {
        try {
            ItemReviewResponseDTO itemReviewResponseDTO = itemS.addItemReviewResponse(itemReviewResponse);
            if (itemReviewResponseDTO != null) {
                return ResponseEntity.ok(itemReviewResponseDTO);
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

    @GetMapping(value = "/getItemReviews/{itemId}/{customerId}")
    public ResponseEntity getItemReviews(@PathVariable String itemId, @PathVariable String customerId) {
        return ResponseEntity.ok(itemS.getItemReviews(itemId, customerId));
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
