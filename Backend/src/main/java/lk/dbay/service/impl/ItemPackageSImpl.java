package lk.dbay.service.impl;

import lk.dbay.dto.ItemCategoryDTO;
import lk.dbay.dto.ItemPackageDTO;
import lk.dbay.entity.*;
import lk.dbay.repository.ItemPackageR;
import lk.dbay.service.ItemPackageS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ItemPackageSImpl implements ItemPackageS {

    @Autowired
    private ItemPackageR itemPackageR;

    @Override
    @Transactional
    public ItemPackageDTO addPackage(ItemPackage itemPackage, MultipartFile[] files) throws Exception {
        try {
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            BusinessProfileCategory businessProfileCategory = itemPackage.getBusinessProfileCategory();
            itemPackage.setItemPackageId("IPK" + businessProfileCategory.getBusinessProfile().getBusinessProId() + format);
            addItemsToItemPackage(itemPackage);
            addImagesToItemPackage(itemPackage, files);
            businessProfileCategory.setBusinessProfileCategoryId(
                    new BusinessProfileCategoryPK(businessProfileCategory.getBusinessProfile().getBusinessProId(), businessProfileCategory.getBusinessCategory().getBusinessCategoryId())
            );
            itemPackageR.save(itemPackage);
            return new ItemPackageDTO(itemPackage, itemPackage.getBusinessProfileCategory(), itemPackage.getItemPackageImgs());
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Something went wrong");
        }
    }

    private void addItemsToItemPackage(ItemPackage itemPackage) {
        for (ItemItemPackage itemItemPackage : itemPackage.getItemItemPackages()) {
            itemItemPackage.setItemItemPackageId(new ItemItemPackagePK(itemItemPackage.getItem().getItemId(), itemPackage.getItemPackageId()));
            itemItemPackage.setItemPackage(itemPackage);
        }
    }

    private void addImagesToItemPackage(ItemPackage itemPackage, MultipartFile[] files) {
        try {
//            Set<ItemImg> itemImgs = new HashSet<>();
            for (ItemPackageImg itemPackageImg : itemPackage.getItemPackageImgs()) {
                itemPackageImg.setItemPackage(itemPackage);
            }
            LocalDateTime localDateTime = LocalDateTime.now();
            String format = localDateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
            int i = 0;
            for (MultipartFile file : files) {
                ItemPackageImg itemPackageImg = new ItemPackageImg();
                itemPackageImg.setItemPackageImgId("ITPKGIMG" + ++i + format);
                itemPackageImg.setItemPackageImg(file.getBytes());
                itemPackageImg.setItemPackageImgName(StringUtils.cleanPath(file.getOriginalFilename()));
                itemPackageImg.setItemPackageImgType(file.getContentType());
                itemPackageImg.setItemPackage(itemPackage);
                itemPackage.getItemPackageImgs().add(itemPackageImg);
            }
//            itemPackage.setItemImgs(itemImgs);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<ItemPackageDTO> getItemPackagesOrdered(String businessProfileId, String businessCategoryId) {
        List<ItemPackage> itemPackageList = itemPackageR.getItemPackagesOrdered(new BusinessProfileCategoryPK(businessProfileId, businessCategoryId));
        List<ItemPackageDTO> itemPackageDTOS = new ArrayList<>();
        for (ItemPackage itemPackage : itemPackageList) {
            itemPackageDTOS.add(new ItemPackageDTO(itemPackage, itemPackage.getBusinessProfileCategory(), itemPackage.getItemPackageImgs()));
        }
        return itemPackageDTOS;
    }

    @Override
    public ItemPackageDTO getItemPackageSelected(String itemPackageId) {
        Optional<ItemPackage> itemPackageOptional = itemPackageR.findById(itemPackageId);
        if (itemPackageOptional.isPresent()) {
            ItemPackage itemPackage = itemPackageOptional.get();
            return new ItemPackageDTO(itemPackage, itemPackage.getBusinessProfileCategory(), itemPackage.getItemItemPackages(), itemPackage.getItemPackageImgs(), true);
        }
        return null;
    }

//    private void addItemsToPackage(ItemPackage itemPackage) {
//        itemPackage.geti
//    }
}
