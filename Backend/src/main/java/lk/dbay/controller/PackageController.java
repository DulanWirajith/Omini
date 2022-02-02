package lk.dbay.controller;

import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.dto.PackageItemDTO;
import lk.dbay.entity.item.PackageItem;
import lk.dbay.service.PackageS;
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
public class PackageController {

    @Autowired
    private PackageS packageS;

    @PostMapping(value = "/addPackage")
    public ResponseEntity addPackage(@RequestPart("package") PackageItem packageItem, @RequestPart("imageFile") MultipartFile[] files) {
        try {
            PackageItemDTO packageItemDTO = packageS.addPackage(packageItem, files);
            if (packageItemDTO != null) {
                return ResponseEntity.ok(packageItemDTO);
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
    public ResponseEntity updatePackage(@RequestPart("package") PackageItem packageItem, @RequestPart("imageFile") MultipartFile[] files, @PathVariable String itemPackageId) {
        try {
            PackageItemDTO packageItemDTO = packageS.updatePackage(packageItem, files, itemPackageId);
            if (packageItemDTO != null) {
                return ResponseEntity.ok(packageItemDTO);
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

    @DeleteMapping(value = "/removePackage/{itemPackageId}")
    public ResponseEntity removePackage(@PathVariable String itemPackageId) {
        try {
            return ResponseEntity.ok(packageS.removePackage(itemPackageId));
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping(value = "/getPackageItemsOrdered/{businessProfileId}/{businessCategoryId}/{start}/{limit}")
    public ResponseEntity getPackageItemsOrdered(@PathVariable String businessProfileId, @PathVariable String businessCategoryId, @PathVariable int start, @PathVariable int limit) {
        return ResponseEntity.ok(packageS.getPackageItemsOrdered(businessProfileId, businessCategoryId, start, limit));
    }

    @GetMapping(value = "/getPackageItemSelected/{itemPackageId}")
    public ResponseEntity getPackageItemSelected(@PathVariable String itemPackageId) {
        return ResponseEntity.ok(packageS.getPackageItemSelected(itemPackageId));
    }

}