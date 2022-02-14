package lk.dbay.service;

import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.entity.CustomerProfile;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerProfileS {

    CustomerProfileDTO addCustomerProfile(CustomerProfile customerProfile, MultipartFile file) throws Exception;

    CustomerProfileDTO getCustomerProfile(String customerProfileId);

    CustomerProfileDTO updateCustomerProfile(CustomerProfile customerProfile, MultipartFile file, String customerProfileId) throws Exception;

//    ShopCartDTO addCart(ShopCart shopCart);
//
//    ShopCartDTO getCart(String cartId);
}
