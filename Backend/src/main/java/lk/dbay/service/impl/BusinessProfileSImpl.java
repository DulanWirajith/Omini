package lk.dbay.service.impl;

import lk.dbay.dto.*;
import lk.dbay.entity.*;
import lk.dbay.entity.item.ItemPackage;
import lk.dbay.entity.item.ItemPackageFavourite;
import lk.dbay.repository.*;
import lk.dbay.service.BusinessProfileS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private DbayUserImgR dbayUserImgR;
    @Autowired
    private BusinessProfileCategoryR businessProfileCategoryR;
    @Autowired
    private ItemPackageR itemPackageR;
    @Value("${image.path}")
    private String filePath;

    @Override
    @Transactional
    public BusinessProfileDTO addBusinessProfile(BusinessProfile businessProfile, MultipartFile[] files) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            businessProfile.getDbayUser().setUserId("U" + format);
//            businessProfile.setBusinessProId("B" + businessProfile.getBusinessRegistrationCode());
            businessProfile.setBusinessProId(businessProfile.getDbayUser().getUserId());
            businessProfile.getDbayUser().setRole("B");
            addBusinessAreas(businessProfile);
            addBusinessProfileCategories(businessProfile);
            addImagesToBusinessProfile(businessProfile.getDbayUser(), files);
//            businessProfile.getDbayUser().setVerificationCode(null);
            dbayUserR.save(businessProfile.getDbayUser());
            businessProfileR.save(businessProfile);
            BusinessProfileDTO businessProfileDTO = new BusinessProfileDTO(businessProfile);
            businessProfileDTO.setDbayUser(businessProfile, false);
            return businessProfileDTO;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    @Override
    public BusinessProfileDTO getBusinessProfile(String businessProfileId, boolean needItems) {
        Optional<BusinessProfile> businessProfileOptional = businessProfileR.findById(businessProfileId);
        if (businessProfileOptional.isPresent()) {
            BusinessProfile businessProfile = businessProfileOptional.get();
            BusinessProfileDTO businessProfileDTO = new BusinessProfileDTO(businessProfile);
            businessProfileDTO.setDbayUser(businessProfile, true);
            businessProfileDTO.setBusinessAreas(businessProfile);
            businessProfileDTO.setBusinessProfileCategories(businessProfile);
            businessProfileDTO.setTown(businessProfile);
            if (needItems) {
                List<ItemPackage> itemsBySearch = new ArrayList<>();
                List<ItemPackage> packagesBySearch = new ArrayList<>();
                ItemPackageDTO itemPackageDTO = new ItemPackageDTO();
                List<ItemPackageDTO> itemPackages = new ArrayList<>();
                List<ItemPackageDTO> items = new ArrayList<>();
                List<ItemPackage> itemPackageList = itemPackageR.getItemsForBusinessProId(businessProfileId);
                for (ItemPackage itemPackage : itemPackageList) {
                    if (itemPackage.getItemPackageType().equals("Item")) {
                        itemsBySearch.add(itemPackage);
                    } else if (itemPackage.getItemPackageType().equals("Package")) {
                        packagesBySearch.add(itemPackage);
                    }
                }
                setItemPackageDTO(itemsBySearch, items);
                setItemPackageDTO(packagesBySearch, itemPackages);
                itemPackageDTO.setItemPackages(itemPackages);
                itemPackageDTO.setItems(items);
                businessProfileDTO.setItemPackage(itemPackageDTO);
            }
            return businessProfileDTO;
        }
        return null;
    }

    private void setItemPackageDTO(List<ItemPackage> itemsBySearch, List<ItemPackageDTO> itemPackages) {
        for (ItemPackage itemBySearch : itemsBySearch) {
            ItemPackageDTO itemPackageDTOObj = new ItemPackageDTO(itemBySearch);
            itemPackageDTOObj.setBusinessProfileCategory(itemBySearch);
            itemPackageDTOObj.setItemPackageImages(itemBySearch);
            itemPackageDTOObj.setOrderDetail(new OrderDetailDTO());
            itemPackages.add(itemPackageDTOObj);
        }
    }

    @Override
    public BusinessProfileDTO updateBusinessProfile(BusinessProfile businessProfile, MultipartFile[] files, String businessProfileId) throws Exception {
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
                businessProfileObj.setDefaultBusiness(businessProfile.getDefaultBusiness());
                businessProfileObj.setBusinessRegistrationCode(businessProfile.getBusinessRegistrationCode());
                businessProfileObj.setBusinessOwner(businessProfile.getBusinessOwner());
                businessProfileObj.setBusinessOwnerNic(businessProfile.getBusinessOwnerNic());
                businessProfileObj.getDbayUser().setUsername(businessProfile.getDbayUser().getUsername());
                businessProfileObj.getDbayUser().setTwoFactorAuth(businessProfile.getDbayUser().isTwoFactorAuth());
                if (!businessProfile.getDbayUser().getPassword().equals("")) {
                    businessProfileObj.getDbayUser().setPassword(businessProfile.getDbayUser().getPassword());
                }

//                for (BusinessArea businessArea : businessProfile.getBusinessAreas()) {
//                    businessArea.setBusinessAreaId(new BusinessAreaPK(businessProfile.getBusinessProId(), businessArea.getTown().getTownId()));
//                    businessArea.setBusinessProfile(businessProfileObj);
//                }
//                addBusinessAreas(businessProfile);
                HashSet<BusinessArea> businessAreas = new HashSet<>(businessProfile.getBusinessAreas());
                businessAreas.retainAll(businessProfileObj.getBusinessAreas());
                Set<BusinessArea> businessAreaSetRemove = new HashSet<>(businessProfileObj.getBusinessAreas());
                businessAreaSetRemove.removeAll(businessAreas);
                Set<BusinessArea> businessAreaSetAdd = new HashSet<>(businessProfile.getBusinessAreas());
                businessAreaSetAdd.removeAll(businessAreas);
                businessProfileObj.setBusinessAreas(businessAreaSetAdd);
                addBusinessAreas(businessProfileObj);
//                HashSet<BusinessArea> businessAreas = new HashSet<>(businessProfile.getBusinessAreas());
//                businessAreas.retainAll(businessProfileObj.getBusinessAreas());
//                Set<BusinessArea> businessAreaSetRemove = new HashSet<>(businessProfileObj.getBusinessAreas());
//                businessAreaSetRemove.removeAll(businessAreas);
//                businessProfile.getBusinessAreas().removeAll(businessAreas);
//                businessProfileObj.setBusinessAreas(businessProfile.getBusinessAreas());

//                for (BusinessProfileCategory businessProfileCategory : businessProfile.getBusinessProfileCategories()) {
//                    businessProfileCategory.setBusinessProfileCategoryId(new BusinessProfileCategoryPK(businessProfile.getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId()));
//                    businessProfileCategory.setBusinessProfile(businessProfileObj);
//                }
//                addBusinessProfileCategories(businessProfile);
                HashSet<BusinessProfileCategory> profileCategories = new HashSet<>(businessProfile.getBusinessProfileCategories());
                profileCategories.retainAll(businessProfileObj.getBusinessProfileCategories());
                Set<BusinessProfileCategory> profileCategorySetRemove = new HashSet<>(businessProfileObj.getBusinessProfileCategories());
                profileCategorySetRemove.removeAll(profileCategories);
                Set<BusinessProfileCategory> profileCategorySetAdd = new HashSet<>(businessProfile.getBusinessProfileCategories());
                profileCategorySetAdd.removeAll(profileCategories);
                businessProfileObj.setBusinessProfileCategories(profileCategorySetAdd);
                addBusinessProfileCategories(businessProfileObj);

                if (businessProfile.getDbayUser().getDbayUserImgs() == null) {
                    businessProfile.getDbayUser().setDbayUserImgs(new HashSet<>());
                }
                HashSet<DbayUserImg> dbayUserImgs = new HashSet<>(businessProfile.getDbayUser().getDbayUserImgs());
                dbayUserImgs.retainAll(businessProfileObj.getDbayUser().getDbayUserImgs());
                Set<DbayUserImg> dbayUserImgsSetRemove = new HashSet<>(businessProfileObj.getDbayUser().getDbayUserImgs());
                dbayUserImgsSetRemove.removeAll(dbayUserImgs);
                Set<DbayUserImg> dbayUserImgsSetAdd = new HashSet<>(businessProfile.getDbayUser().getDbayUserImgs());
                dbayUserImgsSetAdd.removeAll(dbayUserImgs);
                businessProfileObj.getDbayUser().setDbayUserImgs(dbayUserImgsSetAdd);
                addImagesToBusinessProfile(businessProfileObj.getDbayUser(), files);

//                HashSet<BusinessProfileCategory> profileCategories = new HashSet<>(businessProfile.getBusinessProfileCategories());
//                profileCategories.retainAll(businessProfileObj.getBusinessProfileCategories());
//                Set<BusinessProfileCategory> profileCategorySetRemove = new HashSet<>(businessProfileObj.getBusinessProfileCategories());
//                profileCategorySetRemove.removeAll(profileCategories);
//                businessProfile.getBusinessProfileCategories().removeAll(profileCategories);
//                businessProfileObj.setBusinessProfileCategories(businessProfile.getBusinessProfileCategories());

                if (businessAreaSetRemove.size() > 0) {
                    businessAreaR.deleteAll(businessAreaSetRemove);
                }
                if (profileCategorySetRemove.size() > 0) {
                    businessProfileCategoryR.deleteAll(profileCategorySetRemove);
                }
                if (dbayUserImgsSetRemove.size() > 0) {
                    dbayUserImgR.deleteAll(dbayUserImgsSetRemove);
                }
                dbayUserR.save(businessProfileObj.getDbayUser());
                businessProfileR.save(businessProfileObj);
                businessProfileObj.getBusinessAreas().addAll(businessAreas);
                businessProfileObj.getBusinessProfileCategories().addAll(profileCategories);
                BusinessProfileDTO businessProfileDTO = new BusinessProfileDTO();
                businessProfileDTO.setDbayUser(businessProfileObj, true);
//                businessProfileDTO.setBusinessAreas(businessProfile);
//                businessProfileDTO.setBusinessProfileCategories(businessProfile);
                return businessProfileDTO;
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addBusinessAreas(BusinessProfile businessProfile) {
        for (BusinessArea businessArea : businessProfile.getBusinessAreas()) {
            businessArea.setBusinessAreaId(new BusinessAreaPK(businessProfile.getBusinessProId(), businessArea.getTown().getTownId()));
            businessArea.setBusinessProfile(businessProfile);
        }
    }

    private void addBusinessProfileCategories(BusinessProfile businessProfile) {
        for (BusinessProfileCategory businessProfileCategory : businessProfile.getBusinessProfileCategories()) {
            businessProfileCategory.setBusinessProfileCategoryId(new BusinessProfileCategoryPK(businessProfile.getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId()));
            businessProfileCategory.setBusinessProfile(businessProfile);
        }
    }

    private void addImagesToBusinessProfile(DbayUser dbayUser, MultipartFile[] files) {
        try {
//            Set<ItemImg> itemImgs = new HashSet<>();
            for (DbayUserImg dbayUserImg : dbayUser.getDbayUserImgs()) {
                dbayUserImg.setDbayUser(dbayUser);
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int i = 0;
//            String filePath = "C:\\xampp\\htdocs\\Dbay";
            String filePathCur = filePath + "\\business_pro";
            for (MultipartFile file : files) {
                DbayUserImg businessProfileImg = new DbayUserImg();
                businessProfileImg.setUserImgId("BPIMG" + ++i + format);
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
                businessProfileImg.setUserImgName("business_pro/" + StringUtils.cleanPath(file.getOriginalFilename()));
//                itemImg.setItemImgPath("C:\\xampp\\htdocs\\Dbay\\" + itemImg.getItemImgName());
                businessProfileImg.setUserImgType(file.getContentType());
                businessProfileImg.setDbayUser(dbayUser);
                dbayUser.getDbayUserImgs().add(businessProfileImg);
            }
//            item.setItemImgs(itemImgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
