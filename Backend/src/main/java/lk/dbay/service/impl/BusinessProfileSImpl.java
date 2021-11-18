package lk.dbay.service.impl;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.entity.BusinessProfile;
import lk.dbay.repository.BusinessProfileR;
import lk.dbay.repository.DbayUserR;
import lk.dbay.service.BusinessProfileS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BusinessProfileSImpl implements BusinessProfileS {

    @Autowired
    private BusinessProfileR businessProfileR;
    @Autowired
    private DbayUserR dbayUserR;

    @Override
    public BusinessProfileDTO addBusinessProfile(BusinessProfile businessProfile) throws Exception {
        try {
            dbayUserR.save(businessProfile.getUser());
            businessProfileR.save(businessProfile);
            return new BusinessProfileDTO(businessProfile);
        } catch (Exception e) {
            throw new Exception("Something went wrong");
        }
    }
}
