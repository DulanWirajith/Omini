package lk.dbay.service.impl;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.dto.DbayUserDTO;
import lk.dbay.dto.ShopCartDTO;
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
    @Autowired
    private ShopCartR shopCartR;

    @Override
    public CustomerProfileDTO addCustomerProfile(CustomerProfile customerProfile) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            customerProfile.setCustomerProId("C" + format);
            customerProfile.getDbayUser().setUserId("U" + format);
            customerProfile.getDbayUser().setRole("C");

            dbayUserR.save(customerProfile.getDbayUser());
            customerProfileR.save(customerProfile);
            return new CustomerProfileDTO(customerProfile, new DbayUserDTO(customerProfile.getDbayUser()));
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
            return new CustomerProfileDTO(customerProfile, new DbayUserDTO(customerProfile.getDbayUser()));
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
                customerProfileObj.getDbayUser().setUsername(customerProfile.getDbayUser().getUsername());
                customerProfileObj.getDbayUser().setTwoFactorAuth(customerProfile.getDbayUser().isTwoFactorAuth());
                if (!customerProfile.getDbayUser().getPassword().equals("")) {
                    customerProfileObj.getDbayUser().setPassword(customerProfile.getDbayUser().getPassword());
                }

                dbayUserR.save(customerProfileObj.getDbayUser());
                customerProfileR.save(customerProfileObj);
                return new CustomerProfileDTO(customerProfileObj, new DbayUserDTO(customerProfileObj.getDbayUser()));
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    @Override
    public ShopCartDTO addCart(ShopCart shopCart) {
        return new ShopCartDTO(shopCartR.save(shopCart));
    }

    @Override
    public ShopCartDTO getCart(String cartId) {
        Optional<ShopCart> optionalShopCart = shopCartR.findById(cartId);
        if (optionalShopCart.isPresent()) {
            return new ShopCartDTO(optionalShopCart.get());
        }
        return null;
    }
}
