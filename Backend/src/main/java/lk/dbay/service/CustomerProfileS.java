package lk.dbay.service;

import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.dto.DbayUserDTO;
import lk.dbay.dto.ShopCartDTO;
import lk.dbay.entity.CustomerProfile;
import lk.dbay.entity.DbayUser;
import lk.dbay.entity.ShopCart;
import org.springframework.web.multipart.MultipartFile;

public interface CustomerProfileS {

    CustomerProfileDTO addCustomerProfile(CustomerProfile customerProfile, MultipartFile[] files) throws Exception;

    CustomerProfileDTO getCustomerProfile(String customerProfileId);

    CustomerProfileDTO updateCustomerProfile(CustomerProfile customerProfile, MultipartFile[] files, String customerProfileId) throws Exception;

    ShopCartDTO addCart(ShopCart shopCart);

    ShopCartDTO getCart(String cartId);
}
