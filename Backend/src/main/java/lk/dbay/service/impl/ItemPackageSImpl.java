package lk.dbay.service.impl;

import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.ItemPackageR;
import lk.dbay.service.ItemPackageS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class ItemPackageSImpl implements ItemPackageS {

    @Autowired
    private ItemPackageR itemPackageR;

    @Override
    @Transactional
    public ItemPackageDTO addPackage(ItemPackage itemPackage, MultipartFile file) throws Exception {
        try {
            BusinessProfileCategory businessProfileCategory = itemPackage.getBusinessProfileCategory();
            itemPackage.setItemPackageId("IPK" + itemPackage.getName() + businessProfileCategory.getBusinessProfile().getBusinessProId() + businessProfileCategory.getBusinessCategory().getBusinessCategoryId());
            for (ItemItemPackage itemItemPackage : itemPackage.getItemItemPackages()) {
                itemItemPackage.setItemItemPackageId(new ItemItemPackagePK(itemItemPackage.getItem().getItemId(), itemPackage.getItemPackageId()));
                itemItemPackage.setItemPackage(itemPackage);
            }
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
            itemPackage.setItemImg(file.getBytes());
            itemPackage.setItemImgName(StringUtils.cleanPath(file.getOriginalFilename()));
            itemPackage.setItemImgType(file.getContentType());
            return new ItemPackageDTO(itemPackageR.save(itemPackage));
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

//    private void addItemsToPackage(ItemPackage itemPackage) {
//        itemPackage.geti
//    }
}
