package lk.dbay.controller;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.entity.ItemCategory;
import lk.dbay.service.ItemCategoryS;
import lk.dbay.service.ItemPackageS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.PACKAGE_CATEGORY)
public class ItemCategoryController {

    @Autowired
    private ItemCategoryS itemCategoryS;

    @PostMapping(value = "/addCategory")
    public ResponseEntity addCategory(@RequestBody ItemCategory itemCategory) {
        try {
            ItemCategoryDTO itemCategoryDTO = itemCategoryS.addCategory(itemCategory);
            if (itemCategoryDTO != null) {
                return ResponseEntity.ok(itemCategoryDTO);
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
