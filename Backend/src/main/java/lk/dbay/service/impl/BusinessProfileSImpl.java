package lk.dbay.service.impl;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.dto.DbayUserDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.BusinessProfileR;
import lk.dbay.repository.DbayUserR;
import lk.dbay.service.BusinessProfileS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class BusinessProfileSImpl implements BusinessProfileS {

    @Autowired
    private BusinessProfileR businessProfileR;
    @Autowired
    private DbayUserR dbayUserR;

    @Override
    public BusinessProfileDTO addBusinessProfile(BusinessProfile businessProfile) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            businessProfile.setBusinessProId("B" + businessProfile.getBusinessRegistrationCode());
            businessProfile.getUser().setUserId("U" + format);
            businessProfile.getUser().setRole("B");
            for (BusinessArea businessArea : businessProfile.getBusinessAreas()) {
                businessArea.setBusinessAreaId(new BusinessAreaPK(businessProfile.getBusinessProId(), businessArea.getTown().getTownId()));
                businessArea.setBusinessProfile(businessProfile);
            }

            for (BusinessProfileCategory businessProfileCategory : businessProfile.getBusinessProfileCategories()) {
                businessProfileCategory.setBusinessProfileCategoryId(new BusinessProfileCategoryPK(businessProfile.getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId()));
                businessProfileCategory.setBusinessProfile(businessProfile);
            }

            dbayUserR.save(businessProfile.getUser());
            businessProfileR.save(businessProfile);
            return new BusinessProfileDTO(businessProfile, new DbayUserDTO(businessProfile.getUser()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }
}
