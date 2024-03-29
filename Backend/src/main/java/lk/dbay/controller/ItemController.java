package lk.dbay.controller;

import lk.dbay.dto.ItemDTO;
import lk.dbay.entity.item.Item;
import lk.dbay.entity.item.ItemPackageReview;
import lk.dbay.entity.item.ItemPackageReviewResponse;
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

    @PostMapping(value = CommonConstants.SHOP + "/addItem")
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

    @PutMapping(value = CommonConstants.SHOP + "/updateItem/{itemId}")
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

//    @GetMapping(value = "/getItems")
//    public ResponseEntity getItems() {
//        return ResponseEntity.ok(itemS.getItems());
//    }

    @GetMapping(value = CommonConstants.SHOP + "/getItemsBusinessCategory/{businessProfileId}/{businessCategoryId}")
    public ResponseEntity getItemsBusinessCategory(@PathVariable String businessProfileId, @PathVariable String businessCategoryId) {
        return ResponseEntity.ok(itemS.getItemsBusinessCategory(businessProfileId, businessCategoryId));
    }

    @GetMapping(value = CommonConstants.SHOP + "/getItemsOrdered/{businessProfileId}/{businessCategoryId}/{start}/{limit}/{packageOnly}")
    public ResponseEntity getItemsOrdered(@PathVariable String businessProfileId, @PathVariable String businessCategoryId, @PathVariable int start, @PathVariable int limit, @PathVariable boolean packageOnly) {
        return ResponseEntity.ok(itemS.getItemsOrdered(businessProfileId, businessCategoryId, start, limit, packageOnly));
    }

//    @GetMapping(value = "/setItemAvailable/{itemId}")
//    public ResponseEntity setItemAvailable(@PathVariable String itemId) {
//        return ResponseEntity.ok(itemS.setItemAvailable(itemId));
//    }
//
//    @GetMapping(value = "/setItemFavourite/{customerId}/{itemId}")
//    public ResponseEntity setItemFavourite(@PathVariable String customerId, @PathVariable String itemId) {
//        return ResponseEntity.ok(itemS.setItemFavourite(customerId, itemId));
//    }
//
//    @GetMapping(value = "/getItemSelected/{itemId}")
//    public ResponseEntity getItemSelected(@PathVariable String itemId) {
//        return ResponseEntity.ok(itemS.getItemSelected(itemId));
//    }
//
//    @GetMapping(value = "/getItemsPackagesBySearch/{txt}/{category}")
//    public ResponseEntity getItemsPackagesBySearch(@PathVariable String txt, @PathVariable String category) {
//        return ResponseEntity.ok(itemS.getItemsPackagesBySearch(txt, category));
//    }

    @DeleteMapping(value = CommonConstants.SHOP + "/removeItem/{itemId}")
    public ResponseEntity removeItem(@PathVariable String itemId) {
        try {
            return ResponseEntity.ok(itemS.removeItem(itemId));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
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