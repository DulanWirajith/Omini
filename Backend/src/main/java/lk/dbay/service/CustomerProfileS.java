package lk.dbay.service;

import lk.dbay.dto.BusinessProfileDTO;
import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.dto.ShopCartDTO;
import lk.dbay.entity.BusinessProfile;
import lk.dbay.entity.CustomerProfile;
import lk.dbay.entity.ShopCart;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerProfileS {

    CustomerProfileDTO addCustomerProfile(CustomerProfile customerProfile) throws Exception;

    CustomerProfileDTO getCustomerProfile(String customerProfileId);

    CustomerProfileDTO updateCustomerProfile(CustomerProfile customerProfile, MultipartFile[] files, String customerProfileId) throws Exception;

    ShopCartDTO addCart(ShopCart shopCart);

    ShopCartDTO getCart(String cartId);
}
