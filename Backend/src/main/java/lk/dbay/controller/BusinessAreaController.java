package lk.dbay.controller;

import lk.dbay.service.BusinessAreaS;
import lk.dbay.util.CommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin
@RestController
@RequestMapping(value = CommonConstants.DOMAIN_DBAY + CommonConstants.BUSINESS_AREA)
public class BusinessAreaController {

    @Autowired
    private BusinessAreaS businessAreaS;

    @GetMapping(value = "/getBusinessAreas")
    public ResponseEntity getBusinessAreas() {
        return ResponseEntity.ok(businessAreaS.getBusinessAreas());
    }
}
