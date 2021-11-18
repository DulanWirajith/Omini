package lk.dbay.service.impl;

import lk.dbay.dto.BusinessCategoryDTO;
import lk.dbay.entity.BusinessCategory;
import lk.dbay.repository.BusinessCategoryR;
import lk.dbay.service.BusinessCategoryS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessCategorySImpl implements BusinessCategoryS {

    @Autowired
    private BusinessCategoryR businessCategoryR;

    @Override
    public List<BusinessCategoryDTO> getBusinessCategories() {
        List<BusinessCategory> businessCategoryRAll = businessCategoryR.findAll();
        List<BusinessCategoryDTO> businessCategoryDTOS = new ArrayList<>();
        for (BusinessCategory businessCategory : businessCategoryRAll) {
            businessCategoryDTOS.add(new BusinessCategoryDTO(businessCategory));
        }
        return businessCategoryDTOS;
    }
}
