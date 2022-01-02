package lk.dbay.service;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.entity.BusinessProfile;
import org.springframework.web.multipart.MultipartFile;

public interface BusinessProfileS {

    BusinessProfileDTO addBusinessProfile(BusinessProfile businessProfile, MultipartFile[] files) throws Exception;

    BusinessProfileDTO getBusinessProfile(String businessProfileId);

    BusinessProfileDTO updateBusinessProfile(BusinessProfile businessProfile, MultipartFile[] files, String businessProfileId) throws Exception;
}
