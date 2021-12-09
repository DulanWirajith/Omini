package lk.dbay.controller;

import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.ItemPackage;
import lk.dbay.service.ItemPackageS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.PACKAGE)
public class ItemPackageController {

    @Autowired
    private ItemPackageS packageCategoryS;

    @PostMapping(value = "/addPackage")
    public ResponseEntity addPackage(@RequestPart("package") ItemPackage itemPackage, @RequestPart("imageFile") MultipartFile file) {
        try {
            ItemPackageDTO itemPackageDTO = packageCategoryS.addPackage(itemPackage, file);
            if (itemPackageDTO != null) {
                return ResponseEntity.ok(itemPackageDTO);
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
