package lk.dbay.service;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.entity.BusinessProfile;

public interface BusinessProfileS {

    BusinessProfileDTO addBusinessProfile(BusinessProfile businessProfile) throws Exception;
}
