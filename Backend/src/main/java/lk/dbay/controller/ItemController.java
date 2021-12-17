package lk.dbay.controller;

import lk.dbay.dto.ItemDTO;
import lk.dbay.entity.Item;
import lk.dbay.service.ItemS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
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
    public ResponseEntity addItem(@RequestPart("item") Item item, @RequestPart("imageFile") MultipartFile file) {
        try {
            ItemDTO itemDTO = itemS.addItem(item, file);
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

    @GetMapping(value = "/itemImg/{id}")
    public ResponseEntity<byte[]> getItemImg(@PathVariable String id) {

        ItemDTO itemDTO = itemS.getItemImg(id);

        if (itemDTO != null) {
            return ResponseEntity.ok()
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + itemDTO.getItemImgName() + "\"")
                    .body(itemDTO.getItemImg());
        }
        return null;
    }
}
