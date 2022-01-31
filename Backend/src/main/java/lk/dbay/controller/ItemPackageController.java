package lk.dbay.controller;

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
    @GetMapping(value = "/setItemPackageAvailable/{itemId}")
    public ResponseEntity setItemPackageAvailable(@PathVariable String itemId) {
        return ResponseEntity.ok(itemPackageS.setItemPackageAvailable(itemId));
    }

//    @GetMapping(value = "/setItemFavourite/{customerId}/{itemId}")
//    public ResponseEntity setItemFavourite(@PathVariable String customerId, @PathVariable String itemId) {
//        return ResponseEntity.ok(itemPackageS.setItemFavourite(customerId, itemId));
//    }

//    @GetMapping(value = "/getItemPackageSelected/{itemId}")
//    public ResponseEntity getItemPackageSelected(@PathVariable String itemId) {
//        return ResponseEntity.ok(itemPackageS.getItemPackageSelected(itemId));
//    }

    @GetMapping(value = "/getItemsPackagesBySearch/{txt}/{category}")
    public ResponseEntity getItemsPackagesBySearch(@PathVariable String txt, @PathVariable String category) {
        return ResponseEntity.ok(itemPackageS.getItemsPackagesBySearch(txt, category));
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