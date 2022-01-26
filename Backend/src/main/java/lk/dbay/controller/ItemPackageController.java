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
    public ResponseEntity addPackage(@RequestPart("package") ItemPackage itemPackage, @RequestPart("imageFile") MultipartFile[] files) {
        try {
            ItemPackageDTO itemPackageDTO = packageCategoryS.addPackage(itemPackage, files);
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

    @PutMapping(value = "/updatePackage/{itemPackageId}")
    public ResponseEntity updatePackage(@RequestPart("package") ItemPackage itemPackage, @RequestPart("imageFile") MultipartFile[] files, @PathVariable String itemPackageId) {
        try {
            ItemPackageDTO itemPackageDTO = packageCategoryS.updatePackage(itemPackage, files, itemPackageId);
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

    @DeleteMapping(value = "/removePackage/{itemPackageId}")
    public ResponseEntity removePackage(@PathVariable String itemPackageId) {
        try {
            return ResponseEntity.ok(packageCategoryS.removePackage(itemPackageId));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getItemPackagesOrdered/{businessProfileId}/{businessCategoryId}")
    public ResponseEntity getItemPackagesOrdered(@PathVariable String businessProfileId, @PathVariable String businessCategoryId) {
        return ResponseEntity.ok(packageCategoryS.getItemPackagesOrdered(businessProfileId, businessCategoryId));
    }

    @GetMapping(value = "/getItemPackageSelected/{itemPackageId}")
    public ResponseEntity getItemPackageSelected(@PathVariable String itemPackageId) {
        return ResponseEntity.ok(packageCategoryS.getItemPackageSelected(itemPackageId));
    }

}
