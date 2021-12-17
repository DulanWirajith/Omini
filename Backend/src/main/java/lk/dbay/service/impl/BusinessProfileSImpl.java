package lk.dbay.service.impl;

import lk.dbay.dto.*;
import lk.dbay.entity.*;
import lk.dbay.repository.BusinessAreaR;
import lk.dbay.repository.BusinessProfileCategoryR;
import lk.dbay.repository.BusinessProfileR;
import lk.dbay.repository.DbayUserR;
import lk.dbay.service.BusinessProfileS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
public class BusinessProfileSImpl implements BusinessProfileS {

    @Autowired
    private BusinessProfileR businessProfileR;
    @Autowired
    private DbayUserR dbayUserR;
    @Autowired
    private BusinessAreaR businessAreaR;
    @Autowired
    private BusinessProfileCategoryR businessProfileCategoryR;

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

    @Override
    public BusinessProfileDTO getBusinessProfile(String businessProfileId) {
        Optional<BusinessProfile> businessProfileOptional = businessProfileR.findById(businessProfileId);
        if (businessProfileOptional.isPresent()) {
            BusinessProfile businessProfile = businessProfileOptional.get();
            return new BusinessProfileDTO(businessProfile, new DbayUserDTO(businessProfile.getUser()), true, true);
        }
        return null;
    }

    @Override
    public BusinessProfileDTO updateBusinessProfile(BusinessProfile businessProfile, String businessProfileId) throws Exception {
        try {
            Optional<BusinessProfile> businessProfileOptional = businessProfileR.findById(businessProfileId);
            if (businessProfileOptional.isPresent()) {
                BusinessProfile businessProfileObj = businessProfileOptional.get();
                businessProfileObj.setBusinessName(businessProfile.getBusinessName());
                businessProfileObj.setContactNumber1(businessProfile.getContactNumber1());
                businessProfileObj.setContactNumber2(businessProfile.getContactNumber2());
                businessProfileObj.setContactNumber3(businessProfile.getContactNumber3());
                businessProfileObj.setBusinessAddress(businessProfile.getBusinessAddress());
                businessProfileObj.setBusinessEmail(businessProfile.getBusinessEmail());
                businessProfileObj.setBusinessRegistrationCode(businessProfile.getBusinessRegistrationCode());
                businessProfileObj.setBusinessOwner(businessProfile.getBusinessOwner());
                businessProfileObj.setBusinessOwnerNic(businessProfile.getBusinessOwnerNic());
                businessProfileObj.getUser().setUsername(businessProfile.getUser().getUsername());
                businessProfileObj.getUser().setTwoFactorAuth(businessProfile.getUser().isTwoFactorAuth());
                if (!businessProfile.getUser().getPassword().equals("")) {
                    businessProfileObj.getUser().setPassword(businessProfile.getUser().getPassword());
                }

                for (BusinessArea businessArea : businessProfile.getBusinessAreas()) {
                    businessArea.setBusinessAreaId(new BusinessAreaPK(businessProfile.getBusinessProId(), businessArea.getTown().getTownId()));
                    businessArea.setBusinessProfile(businessProfileObj);
                }

                HashSet<BusinessArea> businessAreas = new HashSet<>(businessProfile.getBusinessAreas());
                businessAreas.retainAll(businessProfileObj.getBusinessAreas());
                Set<BusinessArea> businessAreaSetRemove = new HashSet<>(businessProfileObj.getBusinessAreas());
                businessAreaSetRemove.removeAll(businessAreas);
                businessProfile.getBusinessAreas().removeAll(businessAreas);
                businessProfileObj.setBusinessAreas(businessProfile.getBusinessAreas());

                for (BusinessProfileCategory businessProfileCategory : businessProfile.getBusinessProfileCategories()) {
                    businessProfileCategory.setBusinessProfileCategoryId(new BusinessProfileCategoryPK(businessProfile.getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId()));
                    businessProfileCategory.setBusinessProfile(businessProfileObj);
                }

                HashSet<BusinessProfileCategory> profileCategories = new HashSet<>(businessProfile.getBusinessProfileCategories());
                profileCategories.retainAll(businessProfileObj.getBusinessProfileCategories());
                Set<BusinessProfileCategory> profileCategorySetRemove = new HashSet<>(businessProfileObj.getBusinessProfileCategories());
                profileCategorySetRemove.removeAll(profileCategories);
                businessProfile.getBusinessProfileCategories().removeAll(profileCategories);
                businessProfileObj.setBusinessProfileCategories(businessProfile.getBusinessProfileCategories());

                businessAreaR.deleteAll(businessAreaSetRemove);
                businessProfileCategoryR.deleteAll(profileCategorySetRemove);
                dbayUserR.save(businessProfileObj.getUser());
                businessProfileR.save(businessProfileObj);
                businessProfileObj.getBusinessAreas().addAll(businessAreas);
                businessProfileObj.getBusinessProfileCategories().addAll(profileCategories);
                return new BusinessProfileDTO(businessProfileObj, new DbayUserDTO(businessProfileObj.getUser()), true, true);
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }
}
