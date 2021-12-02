package lk.dbay.controller;


import lk.dbay.service.BusinessCategoryS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.BUSINESS_CATEGORY)
public class BusinessCategoryController {

    @Autowired
    private BusinessCategoryS businessCategoryS;

    @GetMapping("/getBusinessCategories")
    public ResponseEntity getBusinessCategories() {
        return ResponseEntity.ok(businessCategoryS.getBusinessCategories());
    }
}
