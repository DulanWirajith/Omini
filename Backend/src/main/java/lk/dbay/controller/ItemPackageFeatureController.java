package lk.dbay.controller;

import lk.dbay.service.ItemPackageFeatureS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.ITEM_PACKAGE_FEATURE)
public class ItemPackageFeatureController {

    @Autowired
    private ItemPackageFeatureS itemPackageFeatureS;

    @GetMapping("/getItemPackageFeatures/{businessCategoryId}")
    public ResponseEntity getItemPackageFeatures(@PathVariable String businessCategoryId) {
        return ResponseEntity.ok(itemPackageFeatureS.getItemPackageFeatures(businessCategoryId));
    }
}