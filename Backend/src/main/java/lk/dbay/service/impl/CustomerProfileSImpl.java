package lk.dbay.service.impl;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.dto.DbayUserDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.*;
import lk.dbay.service.BusinessProfileS;
import lk.dbay.service.CustomerProfileS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class CustomerProfileSImpl implements CustomerProfileS {

    @Autowired
    private CustomerProfileR customerProfileR;
    @Autowired
    private DbayUserR dbayUserR;

    @Override
    public CustomerProfileDTO addCustomerProfile(CustomerProfile customerProfile) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            customerProfile.setCustomerProId("C" + format);
            customerProfile.getUser().setUserId("U" + format);
            customerProfile.getUser().setRole("C");

            dbayUserR.save(customerProfile.getUser());
            customerProfileR.save(customerProfile);
            return new CustomerProfileDTO(customerProfile, new DbayUserDTO(customerProfile.getUser()));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    @Override
    public CustomerProfileDTO getCustomerProfile(String customerProfileId) {
        Optional<CustomerProfile> customerProfileOptional = customerProfileR.findById(customerProfileId);
        if (customerProfileOptional.isPresent()) {
            CustomerProfile customerProfile = customerProfileOptional.get();
            return new CustomerProfileDTO(customerProfile, new DbayUserDTO(customerProfile.getUser()));
        }
        return null;
    }

    @Override
    public CustomerProfileDTO updateCustomerProfile(CustomerProfile customerProfile, String customerProfileId) throws Exception {
        try {
            Optional<CustomerProfile> customerProfileOptional = customerProfileR.findById(customerProfileId);
            if (customerProfileOptional.isPresent()) {
                CustomerProfile customerProfileObj = customerProfileOptional.get();
                customerProfileObj.setCustomerName(customerProfile.getCustomerName());
                customerProfileObj.setContactNumber(customerProfile.getContactNumber());
                customerProfileObj.setCustomerAddress(customerProfile.getCustomerAddress());
                customerProfileObj.setGender(customerProfile.getGender());
                customerProfileObj.getUser().setUsername(customerProfile.getUser().getUsername());
                customerProfileObj.getUser().setTwoFactorAuth(customerProfile.getUser().isTwoFactorAuth());
                if (!customerProfile.getUser().getPassword().equals("")) {
                    customerProfileObj.getUser().setPassword(customerProfile.getUser().getPassword());
                }

                dbayUserR.save(customerProfileObj.getUser());
                customerProfileR.save(customerProfileObj);
                return new CustomerProfileDTO(customerProfileObj, new DbayUserDTO(customerProfileObj.getUser()));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }
}
