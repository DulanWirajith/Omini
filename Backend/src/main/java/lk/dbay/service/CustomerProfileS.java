package lk.dbay.service;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.entity.BusinessProfile;
import lk.dbay.entity.CustomerProfile;

public interface CustomerProfileS {

    CustomerProfileDTO addCustomerProfile(CustomerProfile customerProfile) throws Exception;

    CustomerProfileDTO getCustomerProfile(String customerProfileId);

    CustomerProfileDTO updateCustomerProfile(CustomerProfile customerProfile, String customerProfileId) throws Exception;
}
