package lk.dbay.controller;

import lk.dbay.service.ItemFeatureS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.ITEM_FEATURE)
public class ItemFeatureController {

    @Autowired
    private ItemFeatureS itemFeatureS;

    @GetMapping("/getItemFeatures/{businessCategoryId}")
    public ResponseEntity getItemFeatures(@PathVariable String businessCategoryId) {
        return ResponseEntity.ok(itemFeatureS.getItemFeatures(businessCategoryId));
    }
}
