package lk.dbay.service.impl;

import lk.dbay.dto.CustomerProfileDTO;
import lk.dbay.dto.DbayUserDTO;
import lk.dbay.dto.ShopCartDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.*;
import lk.dbay.service.CustomerProfileS;
import lk.dbay.service.SendEmailSMTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class CustomerProfileSImpl implements CustomerProfileS {

    @Autowired
    private CustomerProfileR customerProfileR;
    @Autowired
    private DbayUserR dbayUserR;
    @Autowired
    private ShopCartR shopCartR;
    @Autowired
    private DbayUserImgR dbayUserImgR;
    @Value("${image.path}")
    private String filePath;
    @Autowired
    private SendEmailSMTP sendEmailSMTP;

    @Override
    public CustomerProfileDTO addCustomerProfile(CustomerProfile customerProfile, MultipartFile[] files) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            customerProfile.getDbayUser().setUserId("U" + format);
//            customerProfile.setBusinessProId("B" + customerProfile.getBusinessRegistrationCode());
            customerProfile.setCustomerProId(customerProfile.getDbayUser().getUserId());
            customerProfile.getDbayUser().setRole("C");
//            customerProfile.setTown();
//            addBusinessAreas(customerProfile);
//            addBusinessProfileCategories(customerProfile);
            addImagesToCustomerProfile(customerProfile.getDbayUser(), files);
            customerProfile.getDbayUser().setVerificationCode(null);
            dbayUserR.save(customerProfile.getDbayUser());
            customerProfileR.save(customerProfile);
            CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO(customerProfile);
            customerProfileDTO.setDbayUser(customerProfile, false);
            return customerProfileDTO;
//            LocalDateTime localDateTime = LocalDateTime.now();
//            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
//            customerProfile.setCustomerProId("C" + format);
//            customerProfile.getDbayUser().setUserId("U" + format);
//            customerProfile.getDbayUser().setRole("C");
//
//            dbayUserR.save(customerProfile.getDbayUser());
//            customerProfileR.save(customerProfile);
//            CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO(customerProfile);
//            customerProfileDTO.setDbayUser(customerProfile, false);
//            return customerProfileDTO;
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
            CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO(customerProfile);
            customerProfileDTO.setDbayUser(customerProfile, true);
            customerProfileDTO.setTown(customerProfile);
            return customerProfileDTO;
        }
        return null;
    }

    @Override
    public CustomerProfileDTO updateCustomerProfile(CustomerProfile customerProfile, MultipartFile[] files, String customerProfileId) throws Exception {
        try {
            Optional<CustomerProfile> customerProfileOptional = customerProfileR.findById(customerProfileId);
            if (customerProfileOptional.isPresent()) {
                CustomerProfile customerProfileObj = customerProfileOptional.get();
                customerProfileObj.setFirstName(customerProfile.getFirstName());
                customerProfileObj.setLastName(customerProfile.getLastName());
                customerProfileObj.setContactNumber1(customerProfile.getContactNumber1());
                customerProfileObj.setContactNumber2(customerProfile.getContactNumber2());
                customerProfileObj.setCustomerAddress(customerProfile.getCustomerAddress());
                customerProfileObj.setGender(customerProfile.getGender());
                customerProfileObj.getDbayUser().setUsername(customerProfile.getDbayUser().getUsername());
                customerProfileObj.getDbayUser().setTwoFactorAuth(customerProfile.getDbayUser().isTwoFactorAuth());
                if (!customerProfile.getDbayUser().getPassword().equals("")) {
                    customerProfileObj.getDbayUser().setPassword(customerProfile.getDbayUser().getPassword());
                }

                if (customerProfile.getDbayUser().getDbayUserImgs() == null) {
                    customerProfile.getDbayUser().setDbayUserImgs(new HashSet<>());
                }
                HashSet<DbayUserImg> dbayUserImgs = new HashSet<>(customerProfile.getDbayUser().getDbayUserImgs());
                dbayUserImgs.retainAll(customerProfileObj.getDbayUser().getDbayUserImgs());
                Set<DbayUserImg> dbayUserImgsSetRemove = new HashSet<>(customerProfileObj.getDbayUser().getDbayUserImgs());
                dbayUserImgsSetRemove.removeAll(dbayUserImgs);
                Set<DbayUserImg> dbayUserImgsSetAdd = new HashSet<>(customerProfile.getDbayUser().getDbayUserImgs());
                dbayUserImgsSetAdd.removeAll(dbayUserImgs);
                customerProfileObj.getDbayUser().setDbayUserImgs(dbayUserImgsSetAdd);
                addImagesToCustomerProfile(customerProfileObj.getDbayUser(), files);

                if (dbayUserImgsSetRemove.size() > 0) {
                    dbayUserImgR.deleteAll(dbayUserImgsSetRemove);
                }

                dbayUserR.save(customerProfileObj.getDbayUser());
                customerProfileR.save(customerProfileObj);
                CustomerProfileDTO customerProfileDTO = new CustomerProfileDTO();
                customerProfileDTO.setDbayUser(customerProfileObj, true);
                return customerProfileDTO;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addImagesToCustomerProfile(DbayUser dbayUser, MultipartFile[] files) {
        try {
//            Set<ItemImg> itemImgs = new HashSet<>();
            for (DbayUserImg dbayUserImg : dbayUser.getDbayUserImgs()) {
                dbayUserImg.setDbayUser(dbayUser);
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int i = 0;
//            String filePath = "C:\\xampp\\htdocs\\Dbay";
            String filePathCur = filePath + "\\customer_pro";
            for (MultipartFile file : files) {
                DbayUserImg customerProfileImg = new DbayUserImg();
                customerProfileImg.setUserImgId("CPIMG" + ++i + format);
//                itemImg.setItemImg(file.getBytes());
                Path root = Paths.get(filePathCur);
                if (!Files.exists(root)) {
                    Files.createDirectories(Paths.get(filePathCur));
                }
                try {
                    Files.copy(file.getInputStream(), root.resolve(file.getOriginalFilename()));
                } catch (FileAlreadyExistsException e) {
                    e.printStackTrace();
                }
                customerProfileImg.setUserImgName("customer_pro/" + StringUtils.cleanPath(file.getOriginalFilename()));
//                itemImg.setItemImgPath("C:\\xampp\\htdocs\\Dbay\\" + itemImg.getItemImgName());
                customerProfileImg.setUserImgType(file.getContentType());
                customerProfileImg.setDbayUser(dbayUser);
                dbayUser.getDbayUserImgs().add(customerProfileImg);
            }
//            item.setItemImgs(itemImgs);
        } catch (Exception e) {
            e.printStackTrace();
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
