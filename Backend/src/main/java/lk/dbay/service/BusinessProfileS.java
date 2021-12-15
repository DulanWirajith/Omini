package lk.dbay.service;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.entity.BusinessProfile;

public interface BusinessProfileS {

    BusinessProfileDTO addBusinessProfile(BusinessProfile businessProfile) throws Exception;

    BusinessProfileDTO getBusinessProfile(String businessProfileId);

    BusinessProfileDTO updateBusinessProfile(BusinessProfile businessProfile, String businessProfileId) throws Exception;
}
